package ru.bpmink.bpm.model.other.processapp;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import ru.bpmink.bpm.model.common.RestEntity;

import java.util.Date;
import java.util.List;

public class ProcessApp extends RestEntity {

    private static final List<InstalledSnapshot> EMPTY_SNAPSHOTS = Lists.newArrayList();

    public ProcessApp() {
    }

    //The ProcessApp identifier.
    @SerializedName("ID")
    private String id;

    //The ProcessApp short name.
    @SerializedName("shortName")
    private String shortName;

    //The ProcessApp name.
    @SerializedName("name")
    private String name;

    //The ProcessApp description.
    @SerializedName("description")
    private String description;

    //The ProcessApp description in html format.
    @SerializedName("richDescription")
    private String richDescription;

    //User that modified snapshot.
    @SerializedName("lastModifiedBy")
    private String lastModifiedBy;

    //For the Process Center, this is the name of the default workspace.
    //For the Process Server, this is the name of the default snapshot.
    @SerializedName("defaultVersion")
    private String defaultVersion;

    //The list of installed snapshots associated with this ProcessApp.
    @SerializedName("installedSnapshots")
    private List<InstalledSnapshot> installedSnapshots = Lists.newArrayList();

    //Date of last processApp modification
    @SerializedName("lastModified_on")
    private Date lastModified;

    /**
     * @return ProcessApp identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * @return ProcessApp short name.
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @return ProcessApp name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return ProcessApp description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return For the Process Center, return the name of the default workspace.
     *      For the Process Server, return the name of the default snapshot.
     */
    public String getDefaultVersion() {
        return defaultVersion;
    }

    /**
     * @return The list of installed snapshots associated with this ProcessApp.
     */
    public List<InstalledSnapshot> getInstalledSnapshots() {
        return MoreObjects.firstNonNull(installedSnapshots, EMPTY_SNAPSHOTS);
    }

    /**
     * @return ProcessApp description in html format.
     */
    public String getRichDescription() {
        return richDescription;
    }

    /**
     * @return User login that modified snapshot.
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * @return The Date of last processApp modification
     */
    public Date getLastModified() {
        if (lastModified != null) {
            return new Date(lastModified.getTime());
        }
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDefaultVersion(String defaultVersion) {
        this.defaultVersion = defaultVersion;
    }

    public void setInstalledSnapshots(List<InstalledSnapshot> installedSnapshots) {
        this.installedSnapshots = Lists.newArrayList(installedSnapshots);
    }

    public void setRichDescription(String richDescription) {
        this.richDescription = richDescription;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * @param lastModified Is date of last processApp modification
     */
    public void setLastModified(Date lastModified) {
        if (lastModified != null) {
            this.lastModified = new Date(lastModified.getTime());
        }
    }

}
