package org.octopus.dashboard.rest.recruit.type;

import java.net.URI;
import java.util.List;

import org.octopus.dashboard.model.recruit.JobGrade;
import org.octopus.dashboard.rest.RestException;
import org.octopus.dashboard.service.recruit.TypeService;
import org.octopus.dashboard.shared.constants.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/v1/type/jobgrade")
public class JobGradeRestController {

	private static Logger logger = LoggerFactory.getLogger(JobGradeRestController.class);

	@Autowired
	private TypeService typeService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<JobGrade> list() {
		return typeService.getAllJobGrade();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public JobGrade get(@PathVariable("id") Long id) {
		JobGrade jobGrade = typeService.getJobGrade(id);
		if (jobGrade == null) {
			String message = "not found (id:" + id + ")";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return jobGrade;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody JobGrade jobGrade, UriComponentsBuilder uriBuilder) {

		typeService.saveJobGrade(jobGrade);

		Long id = jobGrade.getId();
		URI uri = uriBuilder.path("/api/v1/type/jobgrade/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody JobGrade jobGrade) {
		typeService.saveJobGrade(jobGrade);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		typeService.deleteJobGrade(id);
	}
}
