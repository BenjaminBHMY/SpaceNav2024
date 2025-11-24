# SpaceNav2024 🚀

![Java](https://img.shields.io/badge/Java-17-blue.svg?logo=java) ![LibGDX](https://img.shields.io/badge/LibGDX-1.12.1-red.svg?logo=libgdx) ![Gradle](https://img.shields.io/badge/Gradle-8.10-darkblue.svg?logo=gradle)

**SpaceNav2024** es una evolución del clásico arcade *Asteroids*, desarrollado en Java utilizando el framework LibGDX. Este proyecto destaca por la implementación rigurosa de patrones de diseño de software y principios de orientación a objetos (SOLID) para crear una arquitectura escalable y robusta.

---

## 🎮 Características del Juego

### Mecánicas Principales
* **Física Vectorial:** Control de la nave basado en inercia, aceleración (*thrust*) y rotación angular.
* **Mundo Envolvente:** Mecánica de *Screen-Wrapping* (los objetos que salen por un borde reaparecen por el opuesto).
* **Combate Evolutivo:** Sistema de inventario de armas. Desbloquea hasta 7 tipos de disparos diferentes (Doble, Triple, Láser, Abanico, Circular, etc.) acumulando puntuación.
* **Gestión de Daño:** Sistema de vidas y feedback visual/auditivo.

### Entidades y Amenazas
* **Asteroides:** Obstáculos destructibles con 5 variantes visuales.
* **OVNIs:** Enemigos con Inteligencia Artificial básica que acechan al jugador.
    * *Scout (Pequeño):* Rápido, frágil, dispara rápido. Tiene variantes visuales.
    * *Tanque (Grande):* Lento, resistente, dispara proyectiles pesados.
* **Agujeros Negros:** Anomalías espaciales indestructibles. Al colisionar, teletransportan la nave a una ubicación aleatoria del mapa.
* **Power-ups:** Corazones recolectables que recuperan la integridad del casco (+1 Vida).

## 🕹️ Controles

| Tecla | Acción |
| :--- | :--- |
| **⬆️ Flecha Arriba** | Acelerar (Propulsores) |
| **⬅️ ➡️ Izquierda/Derecha** | Rotar la nave |
| **Espacio** | Disparar |
| **C** | Cambiar arma (Ciclar inventario) |

---

## 🛠️ Instalación y Ejecución

### Prerrequisitos
* **Java Development Kit (JDK) 17** o superior.
* **Apache NetBeans IDE** (Recomendado v12+) o IntelliJ IDEA.

### Pasos para ejecutar en NetBeans

1.  **Clonar/Descargar:** Descarga este repositorio en tu equipo.
2.  **Abrir Proyecto:** En NetBeans, ve a `File > Open Project` y selecciona la carpeta raíz `SpaceNav2024`.
    * *Nota:* NetBeans detectará automáticamente que es un proyecto Gradle (icono verde G).
3.  **Configuración:**
    * Asegúrate de que el proyecto use JDK 17 (Click derecho en el proyecto > Properties > Build > Compile > Java Platform).
4.  **Limpiar y Construir:**
    * Click derecho en el proyecto raíz -> **Clean and Build**. (Esto es vital para asegurar que los assets se copien correctamente).
5.  **Ejecutar:**
    * Ve al módulo **`lwjgl3`** (dentro de Sub Projects).
    * Click derecho en `lwjgl3` -> **Run**.

> **Solución de problemas:** Si al ejecutar obtienes un error de "File not found" con imágenes o sonidos, realiza un **Clean and Build** nuevamente para forzar a Gradle a actualizar la carpeta de recursos.

Desarrollado como proyecto de Programación Avanzada.
