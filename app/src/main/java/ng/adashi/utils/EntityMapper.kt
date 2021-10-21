package ng.adashi.utils

interface EntityMapper<ENTITY, DOMAIN_MODEL> {

    fun mapFromEntity(entitiy: ENTITY): DOMAIN_MODEL

    fun mapToEntity(Domain_Model: DOMAIN_MODEL): ENTITY

}