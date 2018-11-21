<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Keep-Board</title>
</head>
<body>
    <!-- Create a form which will have text boxes for Note title, content and status along with a Add
         button. Handle errors like empty fields -->

                <form action = "/add" method = "POST">
                    <div class="form-group">
                    <label for="Title">Title</label>
                    <input class="form-control" name="noteTitle" placeholder="Enter Title">
                  </div>
                  <div class="form-group">
                    <label for="noteContent">Content</label>
                    <input class="form-control" name="noteContent" placeholder="Enter Content">
                  </div>
                  <div class="form-group">
                    <label for="noteStatus">Status</label>
                    <input class="form-control" name="noteStatus" placeholder="Enter Status">
                  </div>
                  <button type="submit" class="btn btn-primary">Submit</button>
                </form>

          <h5 class="card-title">DELETE</h5>
          <form action = "/delete" method = "GET">
             <div class="form-group">
                <label for="noteId">Id</label>
                <input class="form-control" name="noteId" placeholder="Enter Id">
             </div>
             <button type="submit" class="btn btn-primary">Submit</button>
           </form>

          <h5 class="card-title">UPDATE</h5>
          <form action = "/update" method = "POST">
                            <div class="form-group">
                              <label for="noteId">Id</label>
                              <input class="form-control" name="noteId" placeholder="Enter Id">
                            </div>
                            <div class="form-group">
                              <label for="noteTitle">Title</label>
                              <input class="form-control" name="noteTitle" placeholder="Enter Title">
                            </div>
                            <div class="form-group">
                              <label for="noteContent">Content</label>
                              <input class="form-control" name="noteContent" placeholder="Enter Content">
                            </div>
                            <div class="form-group">
                              <label for="noteStatus">Status</label>
                              <input class="form-control" name="noteStatus" placeholder="Enter Status">
                            </div>
                            <button type="submit" class="btn btn-primary">Submit</button>
                          </form>

<!-- display all existing notes in a tabular structure with Id, Title,Content,Status, Created Date and Action -->
    <c:if test="${not empty errorMessage}">
       <p style="color:red">Error</p>: ${errorMessage}
    </c:if>
    <h2>Saved Notes</h2>
    <table border="2">    <tr>
        <td>Title</td>
        <td>Content</td>
        <td>Status</td>
        <td>Created at </td>
    </tr>
    <c:forEach items="${notetitle}" var="note">        <tr>
            <td>${note.noteTitle}</td>        <td>${note.noteContent}</td>        <td>${note.noteStatus}</td>        <td>${note.localDate}</td>        <td>
            </td>
            </tr>
            </c:forEach>
    </table>
    <!-- display all existing notes in a tabular structure with Title,Content,Status, Created Date and Action -->
</body>
</html>