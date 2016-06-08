package org.octopus.dashboard.service.recruit;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.octopus.dashboard.dao.recruit.InterviewDaoRepository;
import org.octopus.dashboard.dao.recruit.InterviewHistoryRepository;
import org.octopus.dashboard.dao.recruit.ResumeDaoRepository;
import org.octopus.dashboard.model.recruit.Interview;
import org.octopus.dashboard.service.email.MailService;
import org.octopus.dashboard.service.recruit.account.ShiroDbRealm.ShiroUser;
import org.octopus.dashboard.shared.persistence.DynamicSpecifications;
import org.octopus.dashboard.shared.persistence.SearchFilter;
import org.octopus.dashboard.shared.persistence.SearchFilter.Operator;
import org.octopus.dashboard.shared.utils.clock.ClockFactory;
import org.octopus.dashboard.shared.utils.clock.IClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class InterviewService {
	@Autowired
	private InterviewDaoRepository interviewDao;
	@Autowired
	private InterviewHistoryRepository interviewHistoryDao;
	@Autowired
	private ResumeDaoRepository resumeDao;
	@Autowired
	private MailService mailService;
	private IClock clock = ClockFactory.getClock();

	public Page<Interview> getResumeInterview(Long resumeId, Map<String, Object> searchParams, int pageNumber,
			int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Interview> spec = buildSpecification(resumeId, searchParams);

		return interviewDao.findAll(spec, pageRequest);
	}

	private Specification<Interview> buildSpecification(Long resumeId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("resume.id", new SearchFilter("resume.id", Operator.EQ, resumeId));
		Specification<Interview> spec = DynamicSpecifications.bySearchFilter(filters.values(), Interview.class);
		return spec;
	}

	public Interview getInterview(Long id) {
		return interviewDao.findOne(id);
	}

	public Interview findJobByComments(String comments) {
		return interviewDao.findByComments(comments);
	}

	public void createInterview(Interview interview) {

		interview.setUpdatedBy(getCurrentUserName());
		interview.setUpdatedTime(clock.getCurrentDate());

		interviewDao.save(interview);

		interviewHistoryDao.save(interview.createAudit());
		// TODO enable email
		// mailService.sendMail(from, to, cc, subject, content);
	}

	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	public void updateInterview(Interview interview) {
		interviewDao.save(interview);

		interviewHistoryDao.save(interview.createAudit());
	}

	public void deleteInterview(Long id) {
		interviewDao.delete(id);
		// resumeDao.deleteByInterviewId(id);

	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "comments");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

}
