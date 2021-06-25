package checkvn.com.viettiepbhdt.data.mappers

interface EntityMapper<ENTITY, DOMAIN> {

    fun mapToDomains(entity: ENTITY): DOMAIN
    fun mapToEntities(domain: DOMAIN): ENTITY
}
