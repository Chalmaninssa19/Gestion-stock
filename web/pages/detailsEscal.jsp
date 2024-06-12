<%@page import="model.gestionFinanciere.Escale"%>
<%@page import="model.gestionBateau.Bateau"%>
<%@page import="model.gestionFinanciere.Prestation"%>
<%@page import="model.gestionQuai.Quai"%>
<%@page import="model.gestionQuai.PrevisionDecale"%>
<%@page import="utilitaire.Util"%>
<%@page import="model.gestionQuai.Proposition"%>
<%@page import="model.gestionBateau.Prevision"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<div class="row">
    <div class="col-lg-6 grid-margin stretch-card">
            <% if(request.getAttribute("message")!=null){ %>
                <div class="alert alert-success" role="alert">
                    <%=request.getAttribute("message")%>
                </div>
            <% }
            if(request.getAttribute("error")!=null){ %>
                <div class="alert alert-success" role="alert">
                    <%=request.getAttribute("error")%>
                </div>
            <% } %>
            <div class="card">
                <% if(request.getAttribute("escale") != null && request.getAttribute("isFacture") != null) { 
                Escale escale = (Escale)request.getAttribute("escale");
                %>
                <p style="margin-left:15%;  width:70%;"><h4>Bateau :<%=escale.getBateau().getNom() %></h4></p>
                <p style="margin-left:15%;  width:70%;">Provenance : <%=escale.getBateau().getPavillon().getNom() %></p>
                <p style="margin-left:15%;  width:70%;">Type bateau : <%=escale.getBateau().getCategorie().getCategorieBateau() %></p>
                <p style="margin-left:15%;  width:70%;">Profondeur : <%=escale.getBateau().getProfondeur() %> m</p>
                <p style="margin-left:15%;  width:70%;">Duree remorquage : <%=escale.getBateau().getDureeRemorquage() %> minutes</p>
                <p style="margin-left:15%;  width:70%;"><h4>Quai actuel :<%=escale.getQuai().getNom() %></h4></p>
                <p style="margin-left:15%;  width:70%;">Profondeur : <%=escale.getQuai().getProfondeur() %> m</p>
          <%    if((boolean)request.getAttribute("isFacture")  == false) { %>
                <p>
                    <a href="changeQuai">
                        <button type="submit" class="btn btn-gradient-primary me-2">Changer de quai</button>
                    </a> 
                    <a href="addPrestation">
                        <button type="submit" class="btn btn-gradient-primary me-2">Ajouter prestation</button>
                    </a>  
                    <a href="finEscale">
                        <button type="submit" class="btn btn-gradient-primary me-2">Fin escale</button>
                    </a>  
                    <a href="identify?id=1">
                        <button type="submit" class="btn btn-gradient-primary me-2">Facturer</button>
                    </a>  
                </p>
                <% } 
                
                else { %>
                <p>
                    <a href="identify?id=3">
                        <button type="submit" class="btn btn-gradient-primary me-2">Voir facture</button>
                    </a>   
                </p>
                <% } } %>
               <div class="card-body">
                    <h4 class="card-title">Liste des prestations</h4>
                    <table class="table">
                         <thead>
                            <tr>
                                <th>Reference</th>
                                <th>Prestation</th>
                                <th>Date debut</th>
                                <th>Date fin</th>
                                <th>Duree</th>
                                <th>Quai</th>
                                <th>Montant</th>
                                <th></th>
                                 <th></th>
                            </tr>
                         </thead>
                         <tbody>
                                     
                           <% if(request.getAttribute("listPrestations") != null) { 
                            ArrayList<Prestation> prestations = (ArrayList<Prestation>)request.getAttribute("listPrestations");
                            for(int i = 0; i < prestations.size(); i++) { 
                               //out.print("io "+prestations.get(i).isValider());
                           %>
                        <tr>
                            <td><%=prestations.get(i).getReference() %></td>
                            <td><%=prestations.get(i).getTypePrestation().getDesignation() %></td>
                            <td><%=prestations.get(i).getDateDebut() %></td>
                             <td><%=prestations.get(i).getDateFin() %></td>
                            <td><%=prestations.get(i).getDuree() %> minutes</td>
                            <td><%=prestations.get(i).getQuai().getNom() %></a></td>
                            <td><%=prestations.get(i).getMontant() %></a> ariary</td>
                             <% if(prestations.get(i).isValider() == false) { %>
                            <td><a href="editPrestation?idPrestation=<%=prestations.get(i).getId() %>">Modifier</a></td>
                            <td><a href="validationPrestation?idPrestation=<%=prestations.get(i).getId() %>">Valider</a></td>
                            <% } %>
                        </tr>
                       <% } } %>

                         </tbody>
                    </table>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>