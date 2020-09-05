-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 05 sep. 2020 à 02:27
-- Version du serveur :  10.4.8-MariaDB
-- Version de PHP : 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `maven_vichshop`
--

-- --------------------------------------------------------

--
-- Structure de la table `boncommande`
--

CREATE TABLE `boncommande` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `dateLivrReel` date DEFAULT NULL,
  `dateLivrVoulu` date DEFAULT NULL,
  `montant` double NOT NULL,
  `numero` varchar(50) NOT NULL,
  `client_id` bigint(20) NOT NULL,
  `etat_bon_id` bigint(20) NOT NULL,
  `etat_payement_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `boncommande`
--

INSERT INTO `boncommande` (`id`, `date`, `dateLivrReel`, `dateLivrVoulu`, `montant`, `numero`, `client_id`, `etat_bon_id`, `etat_payement_id`, `user_id`) VALUES
(1, '2020-08-16', NULL, '2020-08-27', 830000, 'NUM_COM1', 3, 2, 1, 1),
(2, '2020-08-16', NULL, '2020-08-31', 173000, 'NUM_COM2', 1, 1, 1, 1),
(3, '2020-08-16', NULL, '2020-08-11', 216000, 'NUM_COM3', 2, 2, 1, 1),
(4, '2020-08-24', NULL, '2020-08-07', 5000, 'NUM_COM4', 1, 2, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` bigint(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `typeclient_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `email`, `nom`, `typeclient_id`) VALUES
(1, 'info@codify-sn.com', 'CODIFY CORPORATION', 2),
(2, 'bachir@gmail.com', 'Abdoul Bachir', 1),
(3, 'ulrich@codify-sn.com', 'Amah Ulrich', 1);

-- --------------------------------------------------------

--
-- Structure de la table `detailcommande`
--

CREATE TABLE `detailcommande` (
  `id` bigint(20) NOT NULL,
  `qte` int(11) NOT NULL,
  `bon_id` bigint(20) NOT NULL,
  `produit_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `detailcommande`
--

INSERT INTO `detailcommande` (`id`, `qte`, `bon_id`, `produit_id`) VALUES
(1, 1, 1, 2),
(2, 3, 1, 1),
(3, 1, 2, 1),
(4, 1, 2, 5),
(5, 2, 3, 5),
(6, 1, 3, 3),
(7, 2, 3, 6),
(8, 1, 4, 4);

-- --------------------------------------------------------

--
-- Structure de la table `entree`
--

CREATE TABLE `entree` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `qte` int(11) NOT NULL,
  `produit_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `entree`
--

INSERT INTO `entree` (`id`, `date`, `qte`, `produit_id`, `user_id`) VALUES
(1, '2020-08-16', 2, 4, 1),
(2, '2020-08-16', 2, 4, 1);

-- --------------------------------------------------------

--
-- Structure de la table `etatbon`
--

CREATE TABLE `etatbon` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `etatbon`
--

INSERT INTO `etatbon` (`id`, `libelle`) VALUES
(1, 'VALIDE'),
(2, 'NON_VALIDE');

-- --------------------------------------------------------

--
-- Structure de la table `etatpaiement`
--

CREATE TABLE `etatpaiement` (
  `id` bigint(20) NOT NULL,
  `pourcentage` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `etatpaiement`
--

INSERT INTO `etatpaiement` (`id`, `pourcentage`) VALUES
(1, 0),
(2, 30),
(3, 100);

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

CREATE TABLE `facture` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `etat` varchar(50) NOT NULL,
  `mntHt` double NOT NULL,
  `mntTtc` double NOT NULL,
  `mntTva` double NOT NULL,
  `pourcentage` double NOT NULL,
  `remise` double NOT NULL,
  `bon_id` bigint(20) NOT NULL,
  `type_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(50) NOT NULL,
  `prixMin` bigint(20) NOT NULL,
  `prixU` bigint(20) NOT NULL,
  `qteStock` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `libelle`, `prixMin`, `prixU`, `qteStock`, `user_id`) VALUES
(1, 'HP PRO BOOK', 150000, 160000, 15, 1),
(2, 'IPHONE X', 320000, 350000, 5, 1),
(3, 'DISQUE DUR SSD 500 Go', 90000, 120000, 12, 1),
(4, 'SOURIS SANS FIL', 4500, 5000, 10, 1),
(5, 'CLAVIER LUMINEUX', 11500, 13000, 5, 1),
(6, 'RASBERRY PI 3', 30000, 35000, 7, 1);

-- --------------------------------------------------------

--
-- Structure de la table `profil`
--

CREATE TABLE `profil` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `profil`
--

INSERT INTO `profil` (`id`, `libelle`) VALUES
(1, 'ADMIN'),
(2, 'CAISSIER'),
(3, 'MAGASINIER');

-- --------------------------------------------------------

--
-- Structure de la table `sortie`
--

CREATE TABLE `sortie` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `qte` int(11) NOT NULL,
  `produit_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `typeclient`
--

CREATE TABLE `typeclient` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `typeclient`
--

INSERT INTO `typeclient` (`id`, `libelle`) VALUES
(1, 'PARTICULIER'),
(2, 'ENTREPRISE');

-- --------------------------------------------------------

--
-- Structure de la table `typefacture`
--

CREATE TABLE `typefacture` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `nomComplet` varchar(70) NOT NULL,
  `password` varchar(200) NOT NULL,
  `tel` varchar(50) NOT NULL,
  `profil_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `nomComplet`, `password`, `tel`, `profil_id`) VALUES
(1, 'tito@codify-sn.com', 'titovich GB', 'passer', '774988948', 1),
(2, 'admin@gmail.com', 'admin', 'passer', '770001122', 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `boncommande`
--
ALTER TABLE `boncommande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6knoox31bxihh6by5boxyg788` (`client_id`),
  ADD KEY `FK1qaopt39vqkgoainumkdc8y2h` (`etat_bon_id`),
  ADD KEY `FKr813mxvrfpliq34dvsfemx2do` (`etat_payement_id`),
  ADD KEY `FK2k264u9y18h8t2lspnwtj0tp8` (`user_id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmgborx8cosysl2xr9xs3yx42x` (`typeclient_id`);

--
-- Index pour la table `detailcommande`
--
ALTER TABLE `detailcommande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn0w2yhsac2idjg14v9wc0xw5s` (`bon_id`),
  ADD KEY `FKpymrvbo7e53dbk2j75f2wqpwl` (`produit_id`);

--
-- Index pour la table `entree`
--
ALTER TABLE `entree`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpjtp3yit3e7gmhn28dnkjqf84` (`produit_id`),
  ADD KEY `FK9okfgt8atvuvw3h8b35qy667a` (`user_id`);

--
-- Index pour la table `etatbon`
--
ALTER TABLE `etatbon`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `etatpaiement`
--
ALTER TABLE `etatpaiement`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj5g87hyjphj4np38yy764bun2` (`bon_id`),
  ADD KEY `FKhcdxffkdhrpxsrjw1fwwsd2et` (`type_id`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKep645xef18itopc2kajg68wvk` (`user_id`);

--
-- Index pour la table `profil`
--
ALTER TABLE `profil`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `sortie`
--
ALTER TABLE `sortie`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKajcpn5qrnyasp9wa711y1lt8x` (`produit_id`),
  ADD KEY `FKsgwjygt104w77nw2b77ld966g` (`user_id`);

--
-- Index pour la table `typeclient`
--
ALTER TABLE `typeclient`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `typefacture`
--
ALTER TABLE `typefacture`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK88l1ekjabd3nyw7m79hfdqqvv` (`profil_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `boncommande`
--
ALTER TABLE `boncommande`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `detailcommande`
--
ALTER TABLE `detailcommande`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `entree`
--
ALTER TABLE `entree`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `etatbon`
--
ALTER TABLE `etatbon`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `etatpaiement`
--
ALTER TABLE `etatpaiement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `facture`
--
ALTER TABLE `facture`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `profil`
--
ALTER TABLE `profil`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `sortie`
--
ALTER TABLE `sortie`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `typeclient`
--
ALTER TABLE `typeclient`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `typefacture`
--
ALTER TABLE `typefacture`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
