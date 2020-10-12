package com.commscenter.topsecret.message;

import java.util.List;

/**
 * Message Resolver interface. The implementations of this interface are able to
 * use different strategies to resolve a message based on a list of input
 * messages
 * 
 * @author Dario Gonzalez
 */
public interface MessageResolver {

	/**
	 * Return a message based on a list of reference messages.
	 *
	 * @param refereceMessageLists a list of messages to be use as reference for
	 *                             resolving the unified message
	 * @return message resolved
	 * 
	 * @see <a href=
	 *      "https://github.com/dariodavidgonz/commscenter_sos-service/wiki/Message-Resolver:-Merge-Lists">Message-Resolver</a>
	 */
	public String getMessage(List<List<String>> refereceMessageLists);

}