package com.dtos;

import lombok.Data;

/**
 * DTO pour la validation d'une inscription par le responsable.
 */
@Data
public class ValidationDTO {
    // Indique si l'inscription est acceptée (true) ou refusée (false)
    private boolean accept;
}
