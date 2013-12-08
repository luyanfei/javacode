package cn.jhc.controller;

import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.jhc.domain.Spitter;
import cn.jhc.exception.ImageUploadException;
import cn.jhc.service.SpitterService;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

	private final SpitterService spitterService;
	
	@Value("/tmp")
	private String webRootPath;

	@Autowired
	public SpitterController(SpitterService spitterService) {
		super();
		this.spitterService = spitterService;
	}
	
	@RequestMapping(value="/spittles", method=RequestMethod.GET)
	public String listSpittlesForSpitter(
			@RequestParam("spitter") String username, Model model) {
		Spitter spitter = spitterService.getSpitter(username);
		model.addAttribute("spitter", spitter);
		model.addAttribute("spittleList", spitterService.getSpittlesForSpitter(username));
		return "spittles/list";
	}
	
	@RequestMapping(method=RequestMethod.GET, params="new")
	public String createSpitterProfile(Model model) {
		model.addAttribute(new Spitter());
		return "spitters/edit";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addSpitterFromForm(@Valid Spitter spitter, 
			BindingResult bindingResult, 
			@RequestParam(value="image", required=false) MultipartFile image) {
		if(bindingResult.hasErrors())
			return "spitters/edit";
		spitterService.saveSpitter(spitter);
		try {
			if(!image.isEmpty()) {
				validateImage(image);
				saveImage(spitter.getId() + ".jpg", image);
			}
		} catch (ImageUploadException e) {
			bindingResult.reject(e.getMessage());
			return "spitters/edit";
		}
		return "redirect:/spitter/" + spitter.getUsername();
	}
	
	private void saveImage(String filename, MultipartFile image) throws ImageUploadException {
		try {
			File file = new File(webRootPath + "/resources/photos/" + filename);
			FileUtils.writeByteArrayToFile(file, image.getBytes());
		} catch (IOException e) {
			throw new ImageUploadException("Unable to save image " , e );
		}
	}

	private void validateImage(MultipartFile image) throws ImageUploadException {
		if(!image.getContentType().equals("image/jpeg"))
			throw new ImageUploadException("Only JPG image accepted.");
	}

	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable String username, Model model) {
		model.addAttribute(spitterService.getSpitter(username));
		return "spitters/view";
	}
}
