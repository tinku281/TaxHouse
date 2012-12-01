package com.taxhouse.model;

import java.util.Date;
import java.util.List;

public class ArmedForcePersonnel extends Employee {

	private List<SpecialTask> specialTasks;

	public List<SpecialTask> getSpecialTasks() {
		return specialTasks;
	}

	public void setSpecialTasks(List<SpecialTask> specialTasks) {
		this.specialTasks = specialTasks;
	}

	public static class SpecialTask {

		private int id;
		private String name;
		private Date startDate, endDate;
		private String combatZone;

		public SpecialTask(int id, String name, Date startDate, Date endDate,
				String combatZone) {
			super();
			this.id = id;
			this.name = name;
			this.startDate = startDate;
			this.endDate = endDate;
			this.combatZone = combatZone;

		}

		public String getCombatZone() {
			return combatZone;
		}

		public void setCombatZone(String combatZone) {
			this.combatZone = combatZone;
		}

		public int getId() {
			return id;
		}

		public void setId(int taskId) {
			this.id = taskId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
	}

}
