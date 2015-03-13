package org.volifecycle.transco;

import java.util.Map;

/**
 * Interface pour les tables de transco (potentiellement initialis�s dans une
 * conf Spring ou depuis une bdd)
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public interface Transco extends Map<String, String> {
    /**
     * Retourner la map (d�finie dans une conf spring)
     * 
     * @return Map<String, String>
     */
    Map<String, String> getMap();

    /**
     * D�finir la map dans une conf Spring
     * 
     * @param map
     */
    void setMap(Map<String, String> map);

    /**
     * Retourne la clef si valeur non trouv�e
     * 
     * @param key
     * @return
     */
    String getQuietly(String key);

    /**
     * Retourne la clef pour une valeur donn�e
     * 
     * @param value
     * @return
     */
    String getKey(String value);

    /**
     * Retourne la valeur si clef non trouv�e
     * 
     * @param value
     * @return
     */
    String getKeyQuietly(String value);
}

