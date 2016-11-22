package no.juleluka.api.models.mappers;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public final class ModelMappers {
    private ModelMappers() {}

    //TODO: Remove these.. it is not safe to use them since they can be tampered with by client code
    @Deprecated
    public static final ModelMapper LOOSE_MAPPER = new ModelMapper();
    @Deprecated
    public static final ModelMapper STANDARD_MAPPER = new ModelMapper();
    @Deprecated
    public static final ModelMapper STRICT_MAPPER = new ModelMapper();

    {
        LOOSE_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        STANDARD_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        STRICT_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static ModelMapper looseMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper;
    }

    public static ModelMapper looseNonNullMapper() {
        ModelMapper mapper = looseMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

    public static ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper;
    }

    public static ModelMapper nonNullMapper() {
        ModelMapper mapper = mapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

    public static ModelMapper strictMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    public static ModelMapper nonNullStrictMapper() {
        ModelMapper mapper = strictMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

}
