package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.IItemDAO;
import me.heitx.maserow.database.dao.IQuestDAO;
import me.heitx.maserow.database.trinitywotlk.ItemDAO;
import me.heitx.maserow.database.trinitywotlk.QuestDAO;

public class TrinityDatabase implements IDatabase {
	private IClient client;

	private IItemDAO itemDAO;
	private IQuestDAO questDAO;

	TrinityDatabase() {

	}

	@Override
	public IClient getClient() {
		return client;
	}

	@Override
	public void setClient(IClient client) {
		this.client = client;
	}

	@Override
	public IItemDAO getItemDAO() {
		if(Database.isIsLoggedIn() && itemDAO == null) itemDAO = new ItemDAO(client);
		return itemDAO;
	}

	@Override
	public IQuestDAO getQuestDAO() {
		if(Database.isIsLoggedIn() && questDAO == null) questDAO = new QuestDAO(client);
		return questDAO;
	}
}
