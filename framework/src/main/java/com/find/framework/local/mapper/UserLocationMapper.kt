package com.find.framework.local.mapper

import com.find.domain.mapper.Mapper
import com.find.domain.model.Coordinates
import com.find.domain.model.Latitude
import com.find.domain.model.Longitude
import com.find.framework.local.entity.LocationEntry
import javax.inject.Inject


class UserLocationEntryToModelMapper @Inject constructor() : Mapper<LocationEntry, Coordinates> {
    override fun map(input: LocationEntry): Coordinates {
        return Coordinates(
            Latitude(input.latitude),
            Longitude(input.longitude)
        )

    }
}

class CoordinatesToEntryMapper @Inject constructor() : Mapper<Coordinates, LocationEntry> {
    override fun map(input: Coordinates): LocationEntry {
        return LocationEntry(
            latitude = input.latitude.value,
            longitude = input.longitude.value
        )
    }
}