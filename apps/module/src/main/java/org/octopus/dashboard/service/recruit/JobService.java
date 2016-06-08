package org.octopus.dashboard.service.recruit;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.octopus.dashboard.dao.recruit.JobDaoRepository;
import org.octopus.dashboard.dao.recruit.JobHistoryRepository;
import org.octopus.dashboard.dao.recruit.ResumeDaoRepository;
import org.octopus.dashboard.model.recruit.Job;
import org.octopus.dashboard.service.email.MailService;
import org.octopus.dashboard.service.recruit.account.ShiroDbRealm.ShiroUser;
import org.octopus.dashboard.shared.persistence.DynamicSpecifications;
import org.octopus.dashboard.shared.persistence.SearchFilter;
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
public class JobService {
	@Autowired
	private JobDaoRepository jobDao;
	@Autowired
	private JobHistoryRepository jobHistoryDao;
	@Autowired
	private ResumeDaoRepository resumeDao;
	@Autowired
	private MailService mailService;
	private IClock clock = ClockFactory.getClock();

	public List<Job> getAllJob() {
		return (List<Job>) jobDao.findAll();
	}

	public Page<Job> getJobs(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Job> spec = buildSpecification(searchParams);
		return jobDao.findAll(spec, pageRequest);
	}

	private Specification<Job> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Job> spec = DynamicSpecifications.bySearchFilter(filters.values(), Job.class);
		return spec;
	}

	public Job getJob(Long id) {
		return jobDao.findOne(id);
	}

	public Job findJobByName(String name) {
		return jobDao.findByName(name);
	}

	public void createJob(Job Job) {

		Job.setUpdatedBy(getCurrentUserName());
		Job.setUpdatedTime(clock.getCurrentDate());
		Job.setOpenTime(clock.getCurrentDate());

		jobDao.save(Job);

		jobHistoryDao.save(Job.createAudit());
		// TODO enable email
		// mailService.sendMail(from, to, cc, subject, content);
	}

	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	public void updateJob(Job job) {
		jobDao.save(job);

		jobHistoryDao.save(job.createAudit());
	}

	public void deleteJob(Long id) {
		jobDao.delete(id);
		resumeDao.deleteByJobId(id);

	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

}
