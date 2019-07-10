<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="codeu.model.store.basic.UserStore" %>

<!DOCTYPE html>
<html>
<head>
  <title>Administrative Page</title>
  <link rel="stylesheet" href="/css/main.css">
</head>

<body>

  <nav>
  	<a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">
  
  <h1>This is the administrative page</h1>
  
    <ul>
      <% if (request.getAttribute("totalUsers") != null) { %>
        <li>Users: <%= request.getAttribute("totalUsers") %>
        </li>
      <% } %>
      <% if (request.getAttribute("totalConvos") != null) { %>
        <li>Conversations: <%= request.getAttribute("totalConvos") %>
        </li>
      <% } %>
      <% if (request.getAttribute("totalMessages") != null) { %>
        <li>Messages: <%= request.getAttribute("totalMessages") %>
        </li>
      <% } %>
      
      <% if (request.getAttribute("activeUser") != null) { %>
        <li>Most active user: <%= request.getAttribute("activeUser") %>
        </li>
      <% } %>
      <% if (request.getAttribute("newUser") != null) { %>
        <li>Newest user: <%= request.getAttribute("newUser") %>
        </li>
      <% } %>
      <% if (request.getAttribute("wordyUser") != null) { %>
        <li>Wordiest user: <%= request.getAttribute("wordyUser") %>
        </li>
      <% } %>
    </ul>
        
  </div>
  
</body>
</html>
