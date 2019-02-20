package br.com.valhala.agenda.web.listeners.flyway;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.flywaydb.core.Flyway;

import br.com.valhala.agenda.db.FabricaConexoes;

@WebListener
public class FlywayMigrationsListener implements ServletContextListener {

	public FlywayMigrationsListener() {
		super();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Do nothing
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Flyway flyway = Flyway.configure().dataSource(FabricaConexoes.getIntance().getDataSource()).load();
		flyway.repair();
		flyway.migrate();
	}

}
