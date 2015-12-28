package org.i4change.app.remote;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.organization.OrganisationDetailedDTO;
import org.i4change.app.dto.user.OrganisationDTO;
import org.i4change.app.dto.user.OrganisationUserDTO;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.user.Users;

public interface IOrganisationService {

	/**
	 * Loads a List of all available Organizations (Admin-role only)
	 * @param SID
	 * @return
	 */
	public abstract SearchResult getOrganisations(String SID, int start,
			int max, String orderby, boolean asc);

	public abstract List<OrganisationUserDTO> getOrganisationsByUser(String SID);

	public abstract List<Organisation> getAllOrganisations(String SID);

	public abstract Long addPendingOrganization(String SID, String orgName,
			Vector orgPatternsMap);

	/**
	 * get an organisation by a given id
	 * @param SID
	 * @param organisation_id
	 * @deprecated
	 * @return
	 */
	public abstract Organisation getOrganisationById(String SID,
			long organisation_id);

	public abstract OrganisationDTO getOrganisationDTOById(String SID,
			long organisation_id);

	public abstract OrganisationDetailedDTO getOrganisationDetailedById(
			String SID, long organisation_id);

	/**
	 * deletes a organisation by a given id
	 * @param SID
	 * @param organisation_id
	 * @return
	 */
	public abstract Long deleteOrganisation(String SID, long organisation_id);

	/**
	 * adds or updates an Organisation
	 * @param SID
	 * @param organisation_id
	 * @param orgname
	 * @return
	 */
	public abstract Long saveOrUpdateOrganisation(String SID, Map regObjectObj);

	/**
	 * gets all users of a given organisation
	 * @param SID
	 * @param organisation_id
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @return
	 */
	public abstract SearchResult getUsersByOrganisation(String SID,
			long organisation_id, int start, int max, String orderby,
			boolean asc);

	public abstract List<Users> getModeratorsByOrganisationId(String SID,
			long organisation_id);

	public abstract Long addUserToOrganisation(String SID,
			Long organisation_id, Long user_id, String comment);

	public abstract Long deleteUserFromOrganisation(String SID,
			Long organisation_id, Long user_id, String comment);

	/**
	 * @param SID
	 * @param organization_id
	 * @return
	 */
	public abstract Long checkOrganizationStatus(String SID,
			Long organization_id, boolean rememberMe, Long language_id,
			Long organisation_id);

}