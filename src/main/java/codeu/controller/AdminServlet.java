package codeu.controller;

import codeu.model.data.User;
import codeu.model.data.Message;
import codeu.model.data.Conversation;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

public class AdminServlet extends HttpServlet{
	
	/** Store class that gives access to Conversations. */
	private ConversationStore conversationStore;
	
	/** Store class that gives access to Messages. */
	private MessageStore messageStore;
	
	/** Store class that gives access to Users. */
	private UserStore userStore;
    
    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
      this.userStore = userStore;
    }
    
    /**
     * Sets the ConversationStore used by this servlet. This function provides a common setup method
     * for use by the test framework or the servlet's init() function.
     */
    void setConversationStore(ConversationStore conversationStore) {
      this.conversationStore = conversationStore;
    }

    /**
     * Sets the MessageStore used by this servlet. This function provides a common setup method for
     * use by the test framework or the servlet's init() function.
     */
    void setMessageStore(MessageStore messageStore) {
      this.messageStore = messageStore;
    }
    
    /**
     * Set up state for handling requests.
     */
    @Override
    public void init() throws ServletException {
      super.init();
      setConversationStore(ConversationStore.getInstance());
      setMessageStore(MessageStore.getInstance());
      setUserStore(UserStore.getInstance());
    }
	
	/**
	   * This function fires when a user requests the /admin URL. It simply forwards the request to
	   * admin.jsp.
	   */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String username = (String) request.getSession().getAttribute("user");
		
		request.setAttribute("totalUsers", totalUsers());
	    request.setAttribute("totalConvos", totalConvos());
	    request.setAttribute("totalMessages", totalMessages());
	    request.setAttribute("activeUser", activeUser());
	    request.setAttribute("newUser", newUser());
	    request.setAttribute("wordyUser", wordyUser());
	    
	    if (userStore.isAdmin(username)) {
	    	request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
	    }
	    else if (username == null) {
	    	response.sendRedirect("/login");
	    }
	    else {
	    	request.setAttribute("error", "User does not have access to admin page");
	    	request.getRequestDispatcher("/WEB-INF/view/conversations.jsp").forward(request, response);
	    }
	}
	
	/** Returns the number of total registered users */
	private int totalUsers() {
		return this.userStore.totalUsers();
	}
	  
	/** Returns the number of total conversations */
	private int totalConvos() {
		return this.conversationStore.totalConvos();
	}
	
	/** Returns the number of total messages */
	private int totalMessages() {
		return this.messageStore.totalMessages();
	}
	
	/** Returns the most active user */
	private String activeUser() {
		List<User> users = userStore.allUsers();
		int activeCount = 0;
		String activeUser = null;
		for (User user : users) {
			ArrayList<Message> userMessages = messageStore.allMessages(user);
			int messageCount = userMessages.size();
			if (messageCount > activeCount) {
				activeCount = messageCount;
				activeUser = user.getName();
			}
		}
		return activeUser;
	}
	
	/** Returns the newest user */
	private String newUser() {
		return this.userStore.newUser();
	}
	
	/** Returns the wordiest user */
	private String wordyUser() {
		List<User> users = userStore.allUsers();
		int wordyCount = 0;
		String wordyUser = null;
		for (User user : users) {
			ArrayList<Message> userMessages = messageStore.allMessages(user);
			int messageCount = 0;
			for (Message message: userMessages) {
				messageCount += message.getContent().length();
			}
			if (messageCount > wordyCount) {
				wordyCount = messageCount;
				wordyUser = user.getName();
			}
		}
		return wordyUser;
	}
}
