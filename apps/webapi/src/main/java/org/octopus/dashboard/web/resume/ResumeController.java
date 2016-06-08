package org.octopus.dashboard.web.resume;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.octopus.dashboard.model.recruit.Job;
import org.octopus.dashboard.model.recruit.Resume;
import org.octopus.dashboard.model.recruit.User;
import org.octopus.dashboard.service.recruit.JobService;
import org.octopus.dashboard.service.recruit.ResumeService;
import org.octopus.dashboard.service.recruit.account.ShiroDbRealm.ShiroUser;
import org.octopus.dashboard.shared.web.Servlets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/resume")
public class ResumeController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "Auto");
		sortTypes.put("comments", "Comments");
	}

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private JobService jobService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Resume> resumes = resumeService.getResumes(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("resumes", resumes);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "resume/resumeList";
	}

	@RequestMapping(value = "jobresumes", method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "jobId", defaultValue = PAGE_SIZE) long jobId, Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Resume> resumes = resumeService.getJobResumes(jobId, searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("jobName", jobService.getJob(jobId).getName());
		model.addAttribute("resumes", resumes);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "resume/resumeList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("resume", new Resume());
		model.addAttribute("action", "create");
		return "resume/resumeForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Resume newResume, RedirectAttributes redirectAttributes,
			@RequestParam(value = "job") int job) {
		User user = new User(getCurrentUserId());
		newResume.setUpdatedBy(user.getLoginName());

		resumeService.createResume(newResume);
		redirectAttributes.addFlashAttribute("message", "create resume succesfully");
		return "redirect:/resume/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("resume", resumeService.getResume(id));
		model.addAttribute("action", "update");
		return "resume/resumeForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("resume") Resume resume, RedirectAttributes redirectAttributes,
			@RequestParam String jobId) {
		Long id;
		try {
			id = Long.parseLong(jobId);
			Job job = jobService.getJob(id);
			resume.setJob(job);
		} catch (Exception e) {

		}
		resumeService.updateResume(resume);
		redirectAttributes.addFlashAttribute("message", "update resume succesfully");
		return "redirect:/resume/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		resumeService.deleteResume(id);
		redirectAttributes.addFlashAttribute("message", "delete resume succesfully");
		return "redirect:/resume/";
	}

	@ModelAttribute
	public void getResume(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("resume", resumeService.getResume(id));
		}
	}

	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
