package me.heitx.maserow.database.repository;

import me.heitx.maserow.model.Quest;

import java.util.List;

public interface IQuestRepository extends ExtendedRepository<Quest> {
	List<Quest> search(int entry, String logTitle, int limit);
}