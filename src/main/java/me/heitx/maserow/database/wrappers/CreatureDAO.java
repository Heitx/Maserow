package me.heitx.maserow.database.wrappers;

import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.MySqlDatabase;
import me.heitx.maserow.database.dao.ICreatureDAO;
import me.heitx.maserow.utils.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class CreatureDAO extends MySqlDatabase implements ICreatureDAO {
	public CreatureDAO(IClient client) {
		super(client);
	}

	@Override
	public List<Map<String, Object>> search(int entry, String name, int limit) {
		List<Map<String, Object>> set = new ArrayList<>();

		execute(Database.Selection.WORLD, conn -> {
			PreparedStatement ps = executeAndConvertResultToSet(set, conn, Queries.Creature.search(false, entry, name, limit));
			return new Statement[] { ps };
		});

		return set;
	}

	@Override
	public boolean insert(Map<String, Object> map) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			PreparedStatement ps = conn.prepareStatement(Queries.Creature.insert(false, map));
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public boolean update(Map<String, Object> map) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			PreparedStatement ps = conn.prepareStatement(Queries.Creature.update(false, map));
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public boolean delete(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			PreparedStatement ps = conn.prepareStatement(Queries.Creature.delete(false, entry));
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public Map<String, Object> get(int entry) {
		AtomicReference<Map<String, Object>> atomic = new AtomicReference<>();

		execute(Database.Selection.WORLD, conn -> {
			PreparedStatement ps = conn.prepareStatement(Queries.Creature.get(false, entry));
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				atomic.set(convertResultSet(rs));
			}

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public List<Map<String, Object>> getAll(int limit) {
		List<Map<String, Object>> set = new ArrayList<>();

		execute(Database.Selection.WORLD, conn -> {
			PreparedStatement ps = executeAndConvertResultToSet(set, conn, Queries.Creature.getAll(false, limit));
			return new Statement[] { ps };
		});

		return set;
	}

	@Override
	public boolean exists(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			PreparedStatement ps = conn.prepareStatement(Queries.Creature.exists(false, entry));

			ResultSet rs = ps.executeQuery();
			atomic.set(rs.next() && rs.getBoolean(1));

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public long getMaxEntry() {
		AtomicLong atomic = new AtomicLong(-1);

		execute(Database.Selection.WORLD, conn -> {
			PreparedStatement ps = conn.prepareStatement(Queries.Creature.getMaxEntry(false));

			ResultSet rs = ps.executeQuery();
			if(rs.next()) atomic.set(rs.getLong(1));

			return new Statement[] { ps };
		});

		return atomic.get();
	}
}