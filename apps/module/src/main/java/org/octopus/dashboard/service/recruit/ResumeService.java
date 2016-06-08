package org.octopus.dashboard.service.recruit;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.octopus.dashboard.dao.recruit.ResumeDaoRepository;
import org.octopus.dashboard.dao.recruit.ResumeHistoryDaoRepository;
import org.octopus.dashboard.model.recruit.Resume;
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
public class ResumeService {
	@Autowired
	private ResumeDaoRepository resumeDao;
	@Autowired
	private ResumeHistoryDaoRepository resumeHistoryDao;
	private IClock clock = ClockFactory.getClock();

	public Resume getResume(Long id) {
		return resumeDao.findOne(id);
	}

	public void saveResume(Resume entity) {
		resumeDao.save(entity);
	}

	public void deleteResume(Long id) {

		Resume resume = resumeDao.findOne(id);

		resumeDao.delete(id);

		resumeHistoryDao.save(resume.createAudit());
	}

	public List<Resume> getAllResume() {
		return (List<Resume>) resumeDao.findAll();
	}

	public void createResume(Resume resume) {

		resume.setUpdatedBy(getCurrentUserName());
		resume.setUpdatedTime(clock.getCurrentDate());

		resumeDao.save(resume);
	}

	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	public void updateResume(Resume resume) {
		resumeDao.save(resume);

		resumeHistoryDao.save(resume.createAudit());
	}

	public Page<Resume> getJobResumes(Long jobId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Resume> spec = buildSpecification(jobId, searchParams);

		return resumeDao.findAll(spec, pageRequest);
	}

	public Page<Resume> getResumes(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Resume> spec = buildSpecification(searchParams);

		return resumeDao.findAll(spec, pageRequest);
	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	private Specification<Resume> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Resume> spec = DynamicSpecifications.bySearchFilter(filters.values(), Resume.class);
		return spec;
	}

	private Specification<Resume> buildSpecification(Long jobId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("job.id", new SearchFilter("job.id", Operator.EQ, jobId));
		Specification<Resume> spec = DynamicSpecifications.bySearchFilter(filters.values(), Resume.class);
		return spec;
	}

}
