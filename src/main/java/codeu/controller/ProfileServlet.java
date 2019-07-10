package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the profile page. */
public class ProfileServlet extends HttpServlet {

	 /** Store class that gives access to users */
	 private UserStore userStore;

  	/** Sets state for handling profile requests */
  	@Override
  	public void init() throws ServletException {
  		super.init();
  		setUserStore(UserStore.getInstance());
  	}

  	/* Sets the UserStore used by this servlet. This function provides a common setup method for use
  	 * by the test framework or the servlet's init() function.
  	 */
  	void setUserStore(UserStore userStore) {
   		this.userStore = userStore;
  	}

  	/**
   	* This function fires when a user navigates to the profile page.
   	*/
  	@Override
  	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
  		String requestUrl = request.getRequestURI();
			String username = requestUrl.substring("/profile/".length());

			User user = userStore.getUser(username);
			if (user == null) {
	      // user was not found, don't let them add a message
	      request.setAttribute("error", "User was not found");
	      request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
	      return;
	    }
			request.setAttribute("username", username);
			request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
  	}

}
