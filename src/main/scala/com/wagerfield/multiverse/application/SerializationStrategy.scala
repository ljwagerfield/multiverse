package com.wagerfield.multiverse.application

import com.wagerfield.multiverse.domain.shared.Blob


trait SerializationStrategyComponent {
	def serializationStrategy: SerializationStrategy

	/**
	 * Serializes and deserializes objects. 
	 */
	trait SerializationStrategy {
		/**
		 * Maps the provided object onto a deserializable BLOB.
		 * @param obj The object to serialize.
		 * @return Serialized BLOB.
		 */
		def serialize(obj: Any): Blob

		/**
		 * Maps the provided BLOB onto its originating object.
		 * @param blob The BLOB to deserialize.
		 * @return Deserialized object.
		 */
		def deserialize(blob: Blob): Any
	}

}

trait PBSerializationStrategyComponent extends SerializationStrategyComponent {
	/**
	 * Serializes and deserializes objects using Google Protocol Buffers.
	 */
	class PBSerializationStrategy extends SerializationStrategy {
		/**
		 * Maps the provided object onto a deserializable BLOB.
		 * @param obj The object to serialize.
		 * @return Serialized BLOB.
		 */
		def serialize(obj: Any): Blob

		/**
		 * Maps the provided BLOB onto its originating object.
		 * @param blob The BLOB to deserialize.
		 * @return Deserialized object.
		 */
		def deserialize(blob: Blob): Any
	}

}
