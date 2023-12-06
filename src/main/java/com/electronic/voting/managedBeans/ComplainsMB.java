package com.electronic.voting.managedBeans;

import com.electronic.voting.entities.City;
import com.electronic.voting.entities.Complain;
import com.electronic.voting.entities.Status;
import com.electronic.voting.lazyModels.ComplainLazyDataModel;
import com.electronic.voting.services.CityService;
import com.electronic.voting.services.ComplainService;
import com.electronic.voting.services.StatusService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Component
//@RequestScope
@Scope("view")
//@ManagedBean(value = "complainMB")
//@RequestScoped
@Data
public class ComplainsMB implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
    ComplainService complainService;
	
	@Autowired
    CityService cityService;
	
	@Autowired
    StatusService statusService;
	
	
	private List<Complain> complains;
	private  Complain selectedComplain;
	private boolean editMode;
	private List<City> cities;
	private List<Status> statuses;
	private City selectedCity;
	@Autowired
	private ComplainLazyDataModel complainLazyModel;
	
	@PostConstruct
	public void init() {
		
		this.complains = complainService.getAllComplains();
		this.cities = cityService.getAllCities();
		this.statuses = statusService.getAllStatuses();
		
	}
	
	public void openNewComplain() {
		this.selectedComplain = new Complain();
	}
	
	
	public void saveComplain() {
		if(selectedComplain!=null) {
			// to add report date
			if(selectedComplain.getReportDate() == null)
				selectedComplain.setReportDate(new Date());
			
			complainService.saveComplain(selectedComplain);
			loadComplains();
			if(editMode) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"تم تعديل  الشكوي",null));
				editMode=false;
			}else {
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"تم اضافة الشكوي",null));
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لم يتم اضافة الشكوي", "complain can't be null!"));
		}
			
	}

	
	public void deleteComplain() {
		
		if(selectedComplain!=null && selectedComplain.getComplainId() != null) {
			
			complainService.deleteComplain(selectedComplain.getComplainId());
			loadComplains();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"تم حذف الشكوي",null));
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لم يتم حذف الشكوي", "complain can't be null!"));
		}
		
	}
	
	public void loadComplains() {
		this.complains = complainService.getAllComplains();
	}
	
	public void clearSearchFields() {
		complainLazyModel.setComplainCity(null);
		complainLazyModel.setComplainDateFrom(null);
		complainLazyModel.setComplainDateTo(null);
		complainLazyModel.setComplainId(null);
		complainLazyModel.setComplainStatus(null);
		complainLazyModel.setComplainTitle(null);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"تم اعادة تعيين البحث",null));
	}
	
	
	
	public String getServerty(Status status) {
		switch(status.getStatusName()) {
		
		case "Pending":
			return "warning";
		case "Closed":
			return "success";
		default:
			return "info";
		}
		
	}
}
