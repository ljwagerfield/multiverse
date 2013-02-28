package com.wagerfield.multiverse.domain.model.species

import com.wagerfield.multiverse.domain.model.speciesFlagEmblemVector.SpeciesFlagEmblemVectorId
import com.wagerfield.multiverse.domain.model.speciesFlagBackgroundVector.SpeciesFlagBackgroundVectorId

/**
 * Species flag.
 * @param backgroundColor Flag background color.
 * @param emblemPrimaryColor Primary color of the flag emblem.
 * @param emblemSecondaryColor Secondary color of the flag emblem, used for the outline.
 * @param backgroundVectorId Vector to be ued for the flag background.
 * @param emblemVectorId Vector to be ued for the flag foreground.
 */
case class SpeciesFlag(backgroundColor:Int,
                       emblemPrimaryColor:Int,
                       emblemSecondaryColor:Int,
                       backgroundVectorId:SpeciesFlagBackgroundVectorId,
                       emblemVectorId:SpeciesFlagEmblemVectorId)
