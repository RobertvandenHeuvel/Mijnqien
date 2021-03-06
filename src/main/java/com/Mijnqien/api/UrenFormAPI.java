package com.Mijnqien.api;

import java.time.LocalDate;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Mijnqien.Exceptions.DeclaratieFormNotFoundException;
import com.Mijnqien.Exceptions.UrenFormNotFoundException;
import com.Mijnqien.Ondersteunend.EmailServer;
import com.Mijnqien.Ondersteunend.ReadProperties;
import com.Mijnqien.domain.trainee.DeclaratieForm;
import com.Mijnqien.domain.trainee.Stat;
import com.Mijnqien.domain.trainee.UrenForm;
import com.Mijnqien.domain.trainee.UrenPerDag;
import com.Mijnqien.service.UrenFormService;

@Component
@Path("urenform")

public class UrenFormAPI {

	private static final LocalDate LocalDate = null;

	@Autowired
	UrenFormService urenFormService;
	
	@Autowired
	EmailServer emailserver; 

	@GET
	@Produces(MediaType.APPLICATION_JSON)

	public Iterable<UrenForm> getUrenForms() {
		Iterable<UrenForm> urenForms = urenFormService.findAlleUrenForms();
		return urenForms;
	}
	
	@GET
	@Path("/{FormID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGroep(@PathParam("FormID") long FormID) {
		try {
			UrenForm urenForm= urenFormService.findById(FormID);
			return Response.ok(urenForm.getUrenPerDag()).build();
		} catch (UrenFormNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/dummy/{FormID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@PathParam("FormID") long FormID) {
		try {
			UrenForm urenForm= urenFormService.findById(FormID);
			return Response.ok(urenForm).build();
		} catch (UrenFormNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/admin/inafwachting")
	@Produces(MediaType.APPLICATION_JSON)
	public Iterable<UrenForm> getUrenFormsInAfwachting(){
		Iterable<UrenForm> urenForms =urenFormService.findAlleByStat(Stat.INAFWACHTING);
		return urenForms;
	}
	
	@GET
	@Path("/admin/ingediend")
	@Produces(MediaType.APPLICATION_JSON)
	public Iterable<UrenForm> getUrenFormsIngediend(){
		Iterable<UrenForm> urenForms =urenFormService.findAlleByStat(Stat.INGEDIEND);
		return urenForms;
	}
	
	@GET
	@Path("/admin/wijzigen")
	@Produces(MediaType.APPLICATION_JSON)
	public Iterable<UrenForm> getUrenFormsWijzigen(){
		Iterable<UrenForm> urenForms =urenFormService.findAlleByStat(Stat.WIJZIGEN);
		return urenForms;
	}
	
	@GET
	@Path("/admin/goedgekeurd")
	@Produces(MediaType.APPLICATION_JSON)
	public Iterable<UrenForm> getUrenFormsGoedgekeurd(){
		Iterable<UrenForm> urenForms =urenFormService.findAlleByStat(Stat.GOEDGEKEURD);
		return urenForms;
	}

	@POST
	@Path("/newform")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inDatabaseStoppen(UrenForm urenForm) {
		UrenForm urenForm2 = urenFormService.saveUrenForm(urenForm);
		return Response.ok(urenForm2).build();
	}

	@PUT
	@Path("/verzend/{FormID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response verzendUrenForm(@PathParam("FormID") long FormID) {
		try {
			UrenForm urenForm = urenFormService.findById(FormID);
			if(urenForm.getStat() == Stat.INAFWACHTING) {
				urenForm.setStat(Stat.INGEDIEND);
				ReadProperties.readConfig();
				urenForm = urenFormService.saveUrenForm(urenForm);
		//		emailserver.send(ReadProperties.setAdminMail,"Gebruikersnaam" + "urenForm " + urenForm.getMaand().getMonth().toString() + " " + urenForm.getMaand().getYear(),"");
				return Response.status(Status.ACCEPTED).build();
			}
			return Response.status(Status.BAD_REQUEST).build();			
		}catch(UrenFormNotFoundException e){
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Path("/verwerk/{FormID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response verwerkUrenForm(@PathParam("FormID") long FormID,UrenForm urenForm) {
		// check of iemand admin is
		
		try {
			UrenForm uForm= urenFormService.findById(urenForm.getId());
//			if (decForm.getStatus()==Stat.INGEDIEND) {
//				decForm.setStatus(Stat.GOEDGEKEURD);
				ReadProperties.readConfig();
				uForm.setStat(urenForm.getStat());
				uForm = urenFormService.saveUrenForm(uForm);

				emailserver.send("testqien@gmail.com","Urenformulier " + uForm.getNaam() + " "
				+ uForm.getMaand().getMonth().toString() + " " + uForm.getMaand().getYear()
				+ " gewijzigd","Deze heeft nu de status " + uForm.getStat() + ".");

				return Response.status(Status.ACCEPTED).build();
//			}
//			return Response.status(Status.BAD_REQUEST).build();
		} catch (UrenFormNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Path("/afkeur/{FormID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response afkeurUrenForm(@PathParam("FormID") long FormID) {
		try {
			UrenForm urenForm = urenFormService.findById(FormID);
			if(urenForm.getStat() == Stat.INGEDIEND) {
				urenForm.setStat(Stat.WIJZIGEN);
				ReadProperties.readConfig();
				urenForm = urenFormService.saveUrenForm(urenForm);
				emailserver.send(ReadProperties.setAdminMail,"Gebruikersnaam" + "urenForm " + urenForm.getMaand().getMonth().toString() + " " + urenForm.getMaand().getYear(),"");
				return Response.status(Status.ACCEPTED).build();
			}
			return Response.status(Status.BAD_REQUEST).build();			
		}catch(UrenFormNotFoundException e){
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Path("/goedkeur/{FormID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response goedkeurUrenForm(@PathParam("FormID") long FormID) {
		try {
			UrenForm urenForm = urenFormService.findById(FormID);
			if(urenForm.getStat() == Stat.INGEDIEND) {
				urenForm.setStat(Stat.GOEDGEKEURD);
				ReadProperties.readConfig();
				urenForm = urenFormService.saveUrenForm(urenForm);
				emailserver.send(ReadProperties.setAdminMail,"Gebruikersnaam" + "urenForm " + urenForm.getMaand().getMonth().toString() + " " + urenForm.getMaand().getYear(),"");
				return Response.status(Status.ACCEPTED).build();
			}
			return Response.status(Status.BAD_REQUEST).build();			
		}catch(UrenFormNotFoundException e){
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}

}
