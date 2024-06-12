<%@page import="model.local.Store"%>
<%@page import="model.produit.Article"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-md-6 grid-margin stretch-card">
          <div class="card">
               <div class="card-body">
                   <h4>Date validation de sortie mouvement</h4>
                    <form class="forms-sample" action="ValideDate" method="POST">
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date">
                        </div>
                       
                        <button type="submit" class="btn btn-gradient-primary me-2">Valider</button>
                    </form>
                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-success" role="alert" style="color: red">
                        <%=request.getAttribute("error") %>
                    </div>
                <% } %>
               </div>
          </div>

     </div>
</div>
<%@include file="footer.jsp" %>