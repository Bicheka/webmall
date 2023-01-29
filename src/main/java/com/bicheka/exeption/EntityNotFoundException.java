package com.bicheka.exeption;

public class EntityNotFoundException extends RuntimeException { 

        public EntityNotFoundException(String id, Class<?> entity) {
                super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
        }

        public EntityNotFoundException() {
                super("not found");
        }

}