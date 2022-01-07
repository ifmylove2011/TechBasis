package com.xter.list;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/8/2
 * 描述:
 */
public class SortListDemo {

	public static void main(String[] args) {
		Set<Attendee> attendees = new TreeSet<>(new Comparator<Attendee>() {
			@Override
			public int compare(Attendee o1, Attendee o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		Attendee attendee1 = new Attendee();
		attendee1.entryUuid = "004";
		attendee1.name = "ceshi1";
		Attendee attendee2 = new Attendee();
		attendee2.entryUuid = "003";
		attendee2.name = "ceshi2";

		attendees.add(attendee1);
		attendees.add(attendee2);

		System.out.println(attendees.toString());
	}

	static class Attendee implements Serializable {

		public String name;
		public String entryUuid;
		public String middleUri;
		public String rate;
		public int ipProtocolType;

		@Override
		public String toString() {
			return "Attendee{" +
					"name='" + name + '\'' +
					", entryUuid='" + entryUuid + '\'' +
					", middleUri='" + middleUri + '\'' +
					", rate='" + rate + '\'' +
					", ipProtocolType=" + ipProtocolType +
					'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Attendee attendee = (Attendee) o;
			return Objects.equals(entryUuid, attendee.entryUuid);
		}

		@Override
		public int hashCode() {
			return Objects.hash(entryUuid);
		}
	}
}
