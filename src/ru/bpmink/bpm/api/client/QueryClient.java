/**
 * 
 */
package ru.bpmink.bpm.api.client;

import ru.bpmink.bpm.model.query.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Client for task query api actions.
 */
public interface QueryClient {
	
	/**
	 * Use this method to retrieve a list of queries for entity instance data.
	 * This method similar to {@link #listQueries(String, QueryKind, List)} with null parameters.
	 * @return list of queries for entity instance data (see {@link ru.bpmink.bpm.model.query.QueryList}});
	 */
	QueryList listQueries();
	
	/**
	 * Use this method to retrieve a list of queries for entity instance data.
	 * @param processAppName Name of the process application, used for additional filtering. 
	 * 		  	This parameter is intended for future use and has currently no effect on the returned query metadata.
	 * @param kind Specifies the type of queries to be returned. If specified, this must be set to "SAVED_SEARCH".
	 * @param content Specifies a list of content values. A content value is essentially a canonical attribute 
	 * 		 	(see {@link ru.bpmink.bpm.model.query.QueryAttribute}) which specifies the semantic use and implicit
	 * 		 	value range of a query attribute. For example, there might be multiple query attributes that represent a 
	 * 		 	task's priority value, but the content value associated with each one would be TASK.PRIORITY, identifying 
	 * 			those attributes as containing a "task priority" type value.
	 * 		 	When using this parameter, only queries which return the specified content values in their result set are returned. 
	 * 			For example, if you specified {@link ru.bpmink.bpm.model.query.QueryAttribute} content = TASK.PRIORITY, TASK.TKIID
	 * 		 	in your request URI query, then the response will include only those queries that return both content types. 
	 * 			To determine the valid content values, you can use the {@link #queryAttributes} 
	 * 			API and inspect the response details.
	 * @return list of queries for entity instance data (see {@link ru.bpmink.bpm.model.query.QueryList}});
	 */
	QueryList listQueries(@Nullable String processAppName, @Nullable QueryKind kind, @Nullable List<QueryAttribute> content);
	
	/**
	 * Use this method to retrieve a list of entity instances via a query.
	 * @param query Query for task instance data (see {@link ru.bpmink.bpm.model.query.Query});
	 * @param selectedAttributes List of attributes (see {@link ru.bpmink.bpm.model.query.QueryAttribute}), which specifies the attributes that are retrieved by the query.
	 * 		 	If no selected attributes are specified, all of the attributes that are defined on the query are returned.
	 * @param interactionFilter Name of a predefined user interaction (see {@link ru.bpmink.bpm.model.query.InteractionFilter});
	 * 			Note: Using any of the interactionFilter values implies that the query filters by current user, 
	 * 			even if the user has administrative privileges. The effect is the same as if filterByCurrentUser is set to true.
	 * @param processAppName Name of the process application, used for additional filtering.
	 * @param sortAttributes List of sort criteria (see {@link ru.bpmink.bpm.model.query.SortAttribute}) applied to the list of returned process instance entities.
	 * 		  	Each list entry has the form attribute [ASC|DESC] where ascending is the default. 
	 * 		  	The attributes TAD_DESCRIPTION, STATE and KIND cannot be used within sort criteria.
	 * @param size Specifies the maximum number of entities to be returned. If not specified, then the default size defined for the query will be used.
	 * @param filterByCurrentUser This parameter is a flag which indicates whether or not the search results should be restricted 
	 * 			to those entities associated with the current user. A value of false means that an unfiltered search will be performed, 
	 * 			whereas a value of true indicates that the search results will be filtered by the current user. If the current user is 
	 * 			an administrative user, then the default is to perform a filtered search. If the current user is not an administrative user, 
	 * 			then this parameter is ignored and a filtered search is performed.
	 * 			Note: This parameter is used only when the request is being executed by an administrative user and no interactionFilter parameter is set, otherwise it is ignored.
	 * @return detailed information about entity instances (see {@link ru.bpmink.bpm.model.query.QueryResultSet});
	 * @throws IllegalArgumentException if specified {@link ru.bpmink.bpm.model.query.Query} is null
	 */
	QueryResultSet queryEntityList(@Nonnull Query query, @Nullable List<QueryAttribute> selectedAttributes, @Nullable InteractionFilter interactionFilter, @Nullable String processAppName, @Nullable List<SortAttribute> sortAttributes, @Nullable Integer size, @Nullable Boolean filterByCurrentUser);

	/**
	 * Use this method to retrieve the number of entity instances in a query matching specified criteria.
	 * @param query Query for task instance data (see {@link ru.bpmink.bpm.model.query.Query});
	 * @param interactionFilter Name of a predefined user interaction (see {@link ru.bpmink.bpm.model.query.InteractionFilter});
	 * 			Note: Using any of the interactionFilter values implies that the query filters by current user, 
	 * 			even if the user has administrative privileges. The effect is the same as if filterByCurrentUser is set to true.
	 * @param processAppName Name of the process application, used for additional filtering.
	 * @param filterByCurrentUser This parameter is a flag which indicates whether or not the search results should be restricted 
	 * 			to those entities associated with the current user. A value of false means that an unfiltered search will be performed, 
	 * 			whereas a value of true indicates that the search results will be filtered by the current user. If the current user is 
	 * 			an administrative user, then the default is to perform a filtered search. If the current user is not an administrative user, 
	 * 			then this parameter is ignored and a filtered search is performed.
	 * 			Note: This parameter is used only when the request is being executed by an administrative user and no interactionFilter parameter is set, otherwise it is ignored.
	 * @return number of entities in a query matching specified criteria. (see {@link ru.bpmink.bpm.model.query.QueryResultSetCount})
	 * @throws  IllegalArgumentException if specified {@link ru.bpmink.bpm.model.query.Query} is null
	 */
	QueryResultSetCount queryEntityListCount(@Nonnull Query query, @Nullable InteractionFilter interactionFilter, @Nullable String processAppName, @Nullable Boolean filterByCurrentUser);
	
	/**
	 * Use this method to retrieve a list of attributes of a specified query for containing entity instance data.
	 * @param query Query for task instance data (see {@link ru.bpmink.bpm.model.query.Query});
	 * @param processAppName Name of the process application, used for additional filtering. 
	 * 		  	This parameter is intended for future use and has currently no effect on the returned query metadata.
	 * @return all available attributes of specified query (see {@link ru.bpmink.bpm.model.query.QueryAttributes})
	 * @throws IllegalArgumentException if specified {@link ru.bpmink.bpm.model.query.Query} is null
	 */
	QueryAttributes queryAttributes(@Nonnull Query query, @Nullable String processAppName);
	
	
	
}
















