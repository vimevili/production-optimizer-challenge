package com.projedata.production_optimizer.dtos.errors;

import java.time.Instant;

public record StandardErrorInfoDTO(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {}