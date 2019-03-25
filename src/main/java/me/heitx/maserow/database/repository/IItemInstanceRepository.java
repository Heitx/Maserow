package me.heitx.maserow.database.repository;

import me.heitx.maserow.model.ItemInstance;

import java.util.List;

public interface IItemInstanceRepository extends BaseRepository<ItemInstance> {
	List<ItemInstance> search(long guid, int itemEntry, long ownerGuid, long creatorGuid, int limit);
}