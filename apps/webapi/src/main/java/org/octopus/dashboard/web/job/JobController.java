package org.octopus.dashboard.web.job;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.octopus.dashboard.model.recruit.Job;
import org.octopus.dashboard.model.recruit.User;
import org.octopus.dashboard.service.recruit.JobService;
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
@RequestMapping(value = "/job")
public class JobController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "Auto");
		sortTypes.put("name", "Name");
	}

	@Autowired
	private JobService jobService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<Job> jobs = jobService.getJobs(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("jobs", jobs);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "job/jobList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("job", new Job());
		model.addAttribute("action", "create");
		return "job/jobForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Job newJob, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
		newJob.setUpdatedBy(user.getLoginName());

		jobService.createJob(newJob);
		redirectAttributes.addFlashAttribute("message", "create job succesfully");
		return "redirect:/job/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("job", jobService.getJob(id));
		model.addAttribute("action", "update");
		return "job/jobForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("job") Job job, RedirectAttributes redirectAttributes) {
		jobService.updateJob(job);
		redirectAttributes.addFlashAttribute("message", "update job succesfully");
		return "redirect:/job/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		jobService.deleteJob(id);
		redirectAttributes.addFlashAttribute("message", "delete job succesfully");
		return "redirect:/job/";
	}

	@ModelAttribute
	public void getJob(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("job", jobService.getJob(id));
		}
	}

	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
