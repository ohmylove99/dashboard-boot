package org.octopus.dashboard.service.recruit;

import java.util.List;

import org.octopus.dashboard.dao.recruit.EmpTypeDaoRepository;
import org.octopus.dashboard.dao.recruit.InterviewRoundDaoRepository;
import org.octopus.dashboard.dao.recruit.InterviewStatusDaoRepository;
import org.octopus.dashboard.dao.recruit.JobBizDaoRepository;
import org.octopus.dashboard.dao.recruit.JobGradeDaoRepository;
import org.octopus.dashboard.dao.recruit.JobStatusDaoRepository;
import org.octopus.dashboard.dao.recruit.ResumeStatusDaoRepository;
import org.octopus.dashboard.dao.recruit.StatusDaoRepository;
import org.octopus.dashboard.model.recruit.EmpType;
import org.octopus.dashboard.model.recruit.InterviewRound;
import org.octopus.dashboard.model.recruit.InterviewStatus;
import org.octopus.dashboard.model.recruit.JobBiz;
import org.octopus.dashboard.model.recruit.JobGrade;
import org.octopus.dashboard.model.recruit.JobStatus;
import org.octopus.dashboard.model.recruit.ResumeStatus;
import org.octopus.dashboard.model.recruit.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TypeService {
	@Autowired
	private EmpTypeDaoRepository empTypeDao;
	@Autowired
	private JobStatusDaoRepository jobStatusDao;
	@Autowired
	private JobGradeDaoRepository jobGradeDao;
	@Autowired
	private JobBizDaoRepository jobBizDao;
	@Autowired
	private InterviewStatusDaoRepository interviewStatusDao;
	@Autowired
	private InterviewRoundDaoRepository interviewRoundDao;
	@Autowired
	private ResumeStatusDaoRepository resumeStatusDao;

	@Autowired
	private StatusDaoRepository statusDao;

	public Status getStatus(Long id) {
		return statusDao.findOne(id);
	}

	public void saveStatus(Status entity) {
		statusDao.save(entity);
	}

	public void deleteStatus(Long id) {
		statusDao.delete(id);
	}

	public List<Status> getAllStatus() {
		return (List<Status>) statusDao.findAll();
	}

	public List<Status> getAllStatusByType(String type) {
		return (List<Status>) statusDao.findByDtype(type);
	}

	//
	public EmpType getEmpType(Long id) {
		return empTypeDao.findOne(id);
	}

	public void saveEmpType(EmpType entity) {
		empTypeDao.save(entity);
	}

	public void deleteEmpType(Long id) {
		empTypeDao.delete(id);
	}

	//
	public List<EmpType> getAllEmpType() {
		return (List<EmpType>) empTypeDao.findAll();
	}

	public JobStatus getJobStatus(Long id) {
		return jobStatusDao.findOne(id);
	}

	public void saveJobStatus(JobStatus entity) {
		jobStatusDao.save(entity);
	}

	public void deleteJobStatus(Long id) {
		jobStatusDao.delete(id);
	}

	//
	public List<JobStatus> getAllJobStatus() {
		return (List<JobStatus>) jobStatusDao.findAll();
	}

	public JobGrade getJobGrade(Long id) {
		return jobGradeDao.findOne(id);
	}

	public void saveJobGrade(JobGrade entity) {
		jobGradeDao.save(entity);
	}

	public void deleteJobGrade(Long id) {
		jobGradeDao.delete(id);
	}

	public List<JobGrade> getAllJobGrade() {
		return (List<JobGrade>) jobGradeDao.findAll();
	}

	//
	public JobBiz getJobBiz(Long id) {
		return jobBizDao.findOne(id);
	}

	public void saveJobBiz(JobBiz entity) {
		jobBizDao.save(entity);
	}

	public void deleteJobBiz(Long id) {
		jobGradeDao.delete(id);
	}

	public List<JobBiz> getAllJobBiz() {
		return (List<JobBiz>) jobBizDao.findAll();
	}

	//
	public InterviewStatus getInterviewStatus(Long id) {
		return interviewStatusDao.findOne(id);
	}

	public void saveInterviewStatus(InterviewStatus entity) {
		interviewStatusDao.save(entity);
	}

	public void deleteInterviewStatus(Long id) {
		interviewStatusDao.delete(id);
	}

	public List<InterviewStatus> getAllInterviewStatus() {
		return (List<InterviewStatus>) interviewStatusDao.findAll();
	}

	//
	public InterviewRound getInterviewRound(Long id) {
		return interviewRoundDao.findOne(id);
	}

	public void saveInterviewRound(InterviewRound entity) {
		interviewRoundDao.save(entity);
	}

	public void deleteInterviewRound(Long id) {
		interviewRoundDao.delete(id);
	}

	public List<InterviewRound> getAllInterviewRound() {
		return (List<InterviewRound>) interviewRoundDao.findAll();
	}

	//
	public ResumeStatus getResumeStatus(Long id) {
		return resumeStatusDao.findOne(id);
	}

	public void saveResumeStatus(ResumeStatus entity) {
		resumeStatusDao.save(entity);
	}

	public void deleteResumeStatus(Long id) {
		resumeStatusDao.delete(id);
	}

	public List<ResumeStatus> getAllResumeStatus() {
		return (List<ResumeStatus>) resumeStatusDao.findAll();
	}
}
