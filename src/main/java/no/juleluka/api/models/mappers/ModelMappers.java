package no.juleluka.api.models.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public final class ModelMappers {
    private ModelMappers() {}

    public static final ModelMapper LOOSE_MAPPER = new ModelMapper();
    public static final ModelMapper STANDARD_MAPPER = new ModelMapper();
    public static final ModelMapper STRICT_MAPPER = new ModelMapper();

    {
        LOOSE_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        STANDARD_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        STRICT_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

}
