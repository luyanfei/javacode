package cn.jhc.resource;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jhc.annotations.ControllerLog;
import cn.jhc.annotations.ModelLog;
import cn.jhc.annotations.PersistenceLog;

@Dependent
public class LoggerProvider {

	@Produces @ModelLog
	public Logger getModelLogger() {
		return LoggerFactory.getLogger("cn.jhc.model");
	}
	
	@Produces @ControllerLog
	public Logger getControllerLogger() {
		return LoggerFactory.getLogger("cn.jhc.controller");
	}
	
	@Produces @PersistenceLog
	public Logger getPersistenceLogger() {
		return LoggerFactory.getLogger("cn.jhc.persistence");
	}
}
