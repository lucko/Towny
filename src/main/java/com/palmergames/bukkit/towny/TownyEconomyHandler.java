package com.palmergames.bukkit.towny;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Economy handler to interface with Register, Vault or iConomy 5.01 directly.
 *
 * @author ElgarL
 */
@SuppressWarnings("deprecation")
public class TownyEconomyHandler {

	private static Towny plugin = null;
	private static Economy vaultEconomy = null;
	private static EcoType type = EcoType.NONE;
	private static String version = "";

	public static void initialize(Towny plugin) {
		TownyEconomyHandler.plugin = plugin;
	}

	/**
	 * @return the economy type we have detected.
	 */
	public static EcoType getType() {
		return type;
	}

	/**
	 * Are we using any economy system?
	 *
	 * @return true if we found one.
	 */
	public static boolean isActive() {
		return (type != EcoType.NONE);
	}

	/**
	 * @return The current economy providers version string
	 */
	public static String getVersion() {
		return version;
	}

	/**
	 * Internal function to set the version string.
	 *
	 * @param version
	 */
	private static void setVersion(String version) {
		TownyEconomyHandler.version = version;
	}

	/**
	 * Find and configure a suitable economy provider
	 *
	 * @return true if successful.
	 */
	public static Boolean setupEconomy() {
		// Try Vault
		try {
			RegisteredServiceProvider<Economy> vaultEcoProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			if (vaultEcoProvider != null) {
				vaultEconomy = vaultEcoProvider.getProvider();
				setVersion(String.format("%s v%s", "Vault", vaultEcoProvider.getPlugin().getDescription().getVersion()));
				type = EcoType.VAULT;
				return true;
			}
		} catch (NoClassDefFoundError ignored) {
		}

		// No compatible Economy system found.
		return false;
	}

	/**
	 * Attempt to delete the economy account.
	 */
	public static void removeAccount(String accountName) {
		try {
			switch (type) {
				case VAULT: // Attempt to zero the account as Vault provides no delete method.
					if (!vaultEconomy.hasAccount(accountName))
						vaultEconomy.createPlayerAccount(accountName);

					vaultEconomy.withdrawPlayer(accountName, (vaultEconomy.getBalance(accountName)));
					return;

				default:
					break;
			}


		} catch (NoClassDefFoundError ignored) {
		}
	}

	/**
	 * Returns the accounts current balance
	 *
	 * @param accountName
	 * @return double containing the total in the account
	 */
	public static double getBalance(String accountName) {
		switch (type) {
			case VAULT:
				if (!vaultEconomy.hasAccount(accountName))
					vaultEconomy.createPlayerAccount(accountName);

				return vaultEconomy.getBalance(accountName);

			default:
				return 0.0;
		}
	}

	/**
	 * Returns true if the account has enough money
	 *
	 * @param accountName
	 * @param amount
	 * @return true if there is enough in the account
	 */
	public static boolean hasEnough(String accountName, Double amount) {
		return getBalance(accountName) >= amount;
	}

	/**
	 * Attempts to remove an amount from an account
	 *
	 * @param accountName
	 * @param amount
	 * @return true if successful
	 */
	public static boolean subtract(String accountName, Double amount) {
		switch (type) {
			case VAULT:
				if (!vaultEconomy.hasAccount(accountName))
					vaultEconomy.createPlayerAccount(accountName);

				return vaultEconomy.withdrawPlayer(accountName, amount).type == EconomyResponse.ResponseType.SUCCESS;

			default:
				return false;
		}
	}

	/**
	 * Add funds to an account.
	 *
	 * @param accountName
	 * @param amount
	 * @return true if successful
	 */
	public static boolean add(String accountName, Double amount) {
		switch (type) {
			case VAULT:
				if (!vaultEconomy.hasAccount(accountName))
					vaultEconomy.createPlayerAccount(accountName);

				return vaultEconomy.depositPlayer(accountName, amount).type == EconomyResponse.ResponseType.SUCCESS;

			default:
				return false;
		}
	}

	public static boolean setBalance(String accountName, Double amount) {
		switch (type) {
			case VAULT:
				if (!vaultEconomy.hasAccount(accountName))
					vaultEconomy.createPlayerAccount(accountName);

				return vaultEconomy.depositPlayer(accountName, (amount - vaultEconomy.getBalance(accountName))).type == EconomyResponse.ResponseType.SUCCESS;

			default:
				return false;
		}
	}

	/**
	 * Format this balance according to the current economy systems settings.
	 *
	 * @param balance
	 * @return string containing the formatted balance
	 */
	public static String getFormattedBalance(double balance) {
		switch (type) {
			case VAULT:
				try {
					return vaultEconomy.format(balance);
				} catch (Exception ignored) {}

			default:
				return String.format("%.2f", balance);
		}
	}

	public enum EcoType {
		NONE, VAULT
	}

}
