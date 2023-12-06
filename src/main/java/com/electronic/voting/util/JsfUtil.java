package com.electronic.voting.util;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.sun.faces.component.visit.FullVisitContext;

/**
 * @author Ghassan
 *
 *
 */

public class JsfUtil {

    public static void hideDialog(String dialogwdj) {
        PrimeFaces.current().executeScript("PF('" + dialogwdj + "').hide()");
    }

    public static void showDialog(String dialogwdj) {
        PrimeFaces.current().executeScript("PF('" + dialogwdj + "').show()");
    }

    public static void changeKnobValue(String knob,double value) {
        PrimeFaces.current().executeScript("PF('"+knob+"').setValue("+value+")");
    }

    public static void executeJS(String widgetVar,String method) {
        //PF('knob').setValue(38);
        PrimeFaces.current().executeScript("PF('"+widgetVar+"')."+method+";");
        //RequestContext.getCurrentInstance().execute("PF('"+widgetVar+"')."+method+";");
    }

    public static void update(String componants) {
        PrimeFaces.current().ajax().update(componants);
    }

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = 0;// selectOne ? entities.size() + 1 : entities.size();
        if (entities == null) {
            size = 1;
        } else {
            size = selectOne ? entities.size() + 1 : entities.size();
        }

        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "Select");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        facesContext.addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        facesContext.addMessage("successInfo", facesMsg);
    }

    public static void addWarrningMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        facesContext.addMessage(null, facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }


    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter,
                                                       UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static UIComponent findComponent(final String id) {

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });

        return found[0];

    }

    public static UIComponent findComponentInForm(final String formid, final String id) {

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });
        UIComponent form = found[0];
        final UIComponent[] foundcomp = new UIComponent[1];
        form.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    foundcomp[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });
        return foundcomp[0];

    }

    public static void redirectTo(String page) {

        try {
            /* page.xhtml?faces-redirect=true */
            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void openPageInDialog(String pageName,Map<String,Object> options){
        if(options==null){
            options = new HashMap<String, Object>();
            options.put("resizable", false);
            options.put("draggable", false);
            options.put("modal", true);
        }
        //	PrimeFaces.current().openDialog(pageName, options, null);
        //   RequestContext.getCurrentInstance().openDialog(pageName, options, null);
    }

	/*public static void forwardTo(String page) {


		 FacesContext context = FacesContext.getCurrentInstance();
		 context.getApplication().getNavigationHandler().handleNavigation(context, null, page);

		try {
			FacesContext.getCurrentInstance().getExternalContext().dispatch(page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String getTimeFromDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    public static Object getRequestParameterForObject(String key){
		/*HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		Object object = httpServletRequest.getAttribute("committee");*/
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key);

    }
    public static Object getRequestParameterForObject2(String key){
		/*HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		Object object = httpServletRequest.getAttribute("committee");*/
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);

    }
    public static void setRequestParameterForObject(String key, Object object) {
		/*HttpServletRequest httpServletRequest = (HttpServletRequest) FaceHttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		 httpServletRequest.setAttribute("committee", selectedCommittee);*/
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(key,object);
        //FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
    }

    public static void removeRequestParameterForObject(String key) {
        //httpServletRequest.removeAttribute("committee");
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove(key);
    }

    public static void setObjectInHTTPSesion(String key, Object object){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute(key, object);
    }

    public static Object getObjectFromHTTPSesion(String key){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Object object = session.getAttribute(key);
        return object;
    }

    public static void removeObjectFromHTTPSesion(String key){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.removeAttribute(key);
        //session.invalidate();
    }

    public static String getFileExtension(String fullName) {

        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public static void sessionInvalidate(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
    }

    public static ResourceBundle getBundle(String bundleName){
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, FacesContext.getCurrentInstance().getViewRoot().getLocale());
        return bundle;
    }

    public static ResourceBundle getBundleMessage(String bundleName) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, bundleName);
        return bundle;
    }

    public static void executeJS(String jsScript) {
        //PF('knob').setValue(38);
        PrimeFaces.current().executeScript(jsScript);
        //RequestContext.getCurrentInstance().execute("PF('"+widgetVar+"')."+method+";");
    }
}