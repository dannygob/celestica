🔹 Celestica – Intelligent Visual Inspection Android Project

🧠 General Purpose Celestic is a native Android application in Kotlin + Jetpack Compose, designed to automate the visual inspection of industrial components using computer vision and machine learning. Its evolution includes:

🔍 Visual detection and classification of physical objects, with enriched queries via internal databases and external sources.
📐 Identification of technical characteristics in metal parts: 2D dimensions, holes, countersinks, alodine halos, etc.
🚦 Intelligent filtering of invalid objects based on their type and context.
🚗 Future scaling to complex bodies and structures to detect imperfections (bumps, dents, etc.).
🧭 3D extrapolation of views using supervised rotation of the part.
📐 Precise camera calibration using Charuco patterns.
🧿 Tracking using ArUco markers and optional AprilTag.
🧾 QR and barcode scanning to link inspections to batches or manufacturing orders.
📄 Report generation upon request (PDF or Word) with inspection results, measurements, and alerts for failed or critical parts.
🛠️ Core Technologies

📱 Android (Kotlin) + Jetpack Compose
🧠 AI: TensorFlow Lite / PyTorch Mobile
🎥 Image: OpenCV + CameraX
🐍 Python (custom model training)
🧩 MVVM Architecture + Data Layer (Room, Flow)
🧩 System Components

CameraView.kt → Live capture + real-time preprocessing
FrameAnalyzer.kt → Analysis of each frame with AI, edges, detection, measurements
DetectionItem.kt → @Parcelize model with results for each part
DetailsScreen.kt → Interactive view per part with measurements + status 🟢🟡🔴
CalibrationManager.kt → Charuco handling and .json persistence
QRScanner.kt → Code scanner with ML Kit
ReportGenerator.kt → 🆕 New module to generate PDF/Word reports from data in Room
ReportRequestDialog.kt → UI to allow the user to choose whether or not to generate the final report
StatusScreen.kt → System overview with metrics and logs
AppNavigation.kt + MainActivity.kt → Central navigation and screen loading
📊 Phased Goals (Technical Summary) | No. | Feature | Status | Android Technical Description | | 1️⃣ | Live Image Analysis | ✅ Completed | CameraX + OpenCV | | 2️⃣ | Object Classifier | ✅ Implemented | .tflite + pre-tagging | | 3️⃣ | Edge Detection | ✅ Implemented | Canny, Sobel, findContours | | 4️⃣ | Technical classification | ✅ Implemented | AI trained in Python, converted to mobile | | 5️⃣ | Car body inspection | 🔲 Future | Multi-capture + segmentation | | 6️⃣ | 2D plan with measurements | ✅ Implemented | Canvas + calibrated scale | | 7️⃣ | Dynamic display per part | ✅ Implemented | UI Compose + ID + state color | | 8️⃣ | Charuco calibration | ✅ Completed | cv2.aruco, results in .json | | 9️⃣ | ArUco + AprilTag | ✅ Implemented | Native JNI + persistence | | 🔟 | Code scanning | ✅ Active | ML Kit or Android pyzbar | | 🧩 | Inspection saved | ✅ Implemented | Room or local .json export | | 📄 | Report generation | ✅ Implemented | PDF/Word export on request | | 🧩 | Dependency Injection | ✅ Implemented | Hilt | | 🐛 | Error Handling | ✅ Implemented | Sealed class for UI states | | 🧪 | Unit Tests | ✅ Implemented | JUnit and MockK | | ✨ | UI Improvements | ✅ Implemented | Animations and Shimmer effect | | 🔐 | Authentication | ✅ Implemented | Firebase Authentication | | ⚙️ | Settings | ✅ Implemented | Settings screen | | 📏 | 2D Drawing | ✅ Implemented | 2D drawing with measurements | | 🖼️ | Dynamic Display | ✅ Implemented | Dynamic display by part | | 💾 | Save Inspections | ✅ Implemented | Save inspections to database | | 🔬 | Image Processing | ✅ Implemented | Advanced image processing techniques | | 📷 | Camera Calibration | ✅ Implemented | Advanced camera calibration techniques | ✅ Celestic Construction Checklist – By Technical Stages

🧱 1. Basic Project Structure 📦 Files and folders:

MainActivity.kt
AppNavigation.kt
ui/ folder
model/ folder
data/ folder
theme/ folder
utils/ folder 🎨 XML Resources:
colors.xml
strings.xml
dimens.xml
themes.xml 🎨 Theme and styles:
CelesticTheme.kt
Typography.kt
Shape.kt (optional for borders)
🧩 2. Data Model + Persistence 📄 Models:

DetectionItem.kt
DetectionStatus.kt (enum 🟢🟡🔴)
BoundingBox.kt
ReportEntry.kt
CameraCalibrationData.kt
DetectedFeature.kt
ReportConfig.kt 🗃️ Room Database:
DetectionDao.kt
DetectionDatabase.kt
DetectionRepository.kt 📁 External Files:
calibration.json
traceability.json (for QR code reading)
config_report.json (user settings)
🎥 3. Camera + Image Analysis Module 📷 Capture:

CameraView.kt
CameraUtils.kt 🔍 Processing:
FrameAnalyzer.kt
Function: detectEdges()
Function: detectMarkers()
Function: classifyImageAI()
Function: applyCalibration()
Function: extractDimensionsFromContours() 📐 Calibration:
CalibrationManager.kt
detectCharucoPattern()
generateCalibrationMatrix()
saveCalibrationToJson()
loadCalibrationFromJson()
🧠 4. Integrated Artificial Intelligence 🧠 Android AI:

.tflite or .pt model saved in assets/
ImageClassifier.kt
Function: runInference(bitmap)
Function: mapPredictionToFeatureType() 🐍 Python AI:
Training script train_model.py
Labeled dataset data/train_images/
Android-compatible export
📊 5. Interface and Screens 🖼️ Compose Screens:

DashboardScreen.kt
CameraScreen.kt
DetailsScreen.kt
ReportRequestDialog.kt
InspectionPreviewScreen.kt (optional preview)
CalibrationScreen.kt (for manual configuration) 📦 Composable Components:
FeatureCard.kt
StatusIndicator.kt
MeasurementOverlay.kt 📍 Navigation:
NavigationRoutes.kt
NavigationGraph.kt
🧾 6. QR / ArUco / AprilTag Traceability 🧾 Scanning:

QRScanner.kt
Function: startQrScan()
Function: decodeBarcode()
Linking to DetectionItem 🔲 Markers:
ArUcoManager.kt
AprilTagManager.kt (via JNI or integrated library) 🗃️ Linked Data:
Traceability.json database
Visual connection in DetailsScreen.kt
📄 7. Inspection Report (PDF / Word / JSON) 📄 Generation:

ReportGenerator.kt
generatePdfFromDetections()
generateWordFromDetections()
exportJsonSummary()
filterDetectionsByStatus() 🖼️ UI:
Button in DashboardScreen.kt or DetailsScreen.kt
Selector: PDF, Word or JSON 📁 Export:
Folder /storage/emulated/0/Celestic/Reports/
Suggested name: ReporteCelestic_Lote123.pdf
🎨 8. Visual Resources 📷 Image / Icons in drawable/:

charuco_pattern.png
logo_celestic.png
icon_inspection.png
icon_pdf.png, icon_word.png
status_green.png, status_yellow.png, status_red.png
graph_placeholder.png

## Post-production
- Create a base application to add modules.
- Develop modules for other domains like automotive, aeronautics, etc.
- ML Kit will be used for the base module for multiplatform.
🔹 Celestic – Proyecto Android de Inspección Visual Inteligente

🧠 Propósito General Celestic es una aplicación Android nativa en Kotlin + Jetpack Compose, diseñada para automatizar la inspección visual de componentes industriales, usando visión computacional y aprendizaje automático. Su evolución contempla:

🔍 Detección y clasificación visual de objetos físicos, con consultas enriquecidas vía bases internas y fuentes externas.
📐 Identificación de características técnicas en piezas metálicas: dimensiones 2D, agujeros, avellanados, halos de alodine, etc.
🚦 Filtrado inteligente de objetos no válidos según su tipo y contexto.
🚗 Escalamiento futuro hacia carrocerías y estructuras complejas para detectar imperfecciones ( golpes, abolladuras, etc.).
🧭 Extrapolación 3D de vistas mediante rotación supervisada de la pieza.
📐 Calibración precisa de la cámara usando patrones Charuco.
🧿 Seguimiento mediante marcadores ArUco y opcionalmente AprilTag.
🧾 Escaneo de códigos QR y barras para vincular inspecciones con lotes u órdenes de fabricación.
📄 Generación de reporte bajo solicitud (PDF o Word) con resultados de inspección, medidas y alertas por piezas falladas o críticas.
🛠️ Tecnologías Base

📱 Android (Kotlin) + Jetpack Compose
🧠 IA: TensorFlow Lite / PyTorch Mobile
🎥 Imagen: OpenCV + CameraX
🐍 Python (entrenamiento de modelos personalizados)
🧩 Arquitectura MVVM + Data Layer (Room, Flow)
🧩 Componentes del Sistema

CameraView.kt → Captura en vivo + preprocesamiento en tiempo real
FrameAnalyzer.kt → Análisis de cada frame con IA, bordes, detección, mediciones
DetectionItem.kt → Modelo @Parcelize con resultados de cada pieza
DetailsScreen.kt → Vista interactiva por pieza con medidas + estado 🟢🟡🔴
CalibrationManager.kt → Manejo de Charuco y persistencia .json
QRScanner.kt → Scanner de códigos con ML Kit
ReportGenerator.kt → 🆕 Nuevo módulo para generar reportes PDF/Word desde los datos en Room
ReportRequestDialog.kt → UI para permitir al usuario elegir generar o no el reporte final
StatusScreen.kt → Vista general del sistema con métricas + logs
AppNavigation.kt + MainActivity.kt → Navegación central y carga de pantallas
📊 Objetivos por Etapas (Resumen técnico) | Nº | Función | Estado | Descripción técnica Android | | 1️⃣ | Análisis de imagen en vivo | ✅ | CameraX + OpenCV | | 2️⃣ | Clasificador de objetos | ✅ Implementado | .tflite + preetiquetado | | 3️⃣ | Detección de bordes | ✅ Implementado | Canny, Sobel, findContours | | 4️⃣ | Clasificación técnica | ✅ Implementado | IA entrenada en Python, convertida a móvil | | 5️⃣ | Inspección de carrocerías | 🔲 Futuro | Captura múltiple + segmentación | | 6️⃣ | Plano 2D con medidas | ✅ Implementado | Canvas + escala calibrada | | 7️⃣ | Pantalla dinámica por pieza | ✅ Implementado | UI Compose + ID + color de estado | | 8️⃣ | Calibración Charuco | ✅ | cv2.aruco, resultados en .json | | 9️⃣ | ArUco + AprilTag | ✅ Implementado | JNI nativo + persistencia | | 🔟 | Escaneo de códigos | ✅ | ML Kit o pyzbar Android | | 🧩 | Guardado de inspección | ✅ Implementado | Room o export .json local | | 📄 | Generación de reporte | ✅ Implementado | Exportador PDF/Word por solicitud | | 🧩 | Inyección de Dependencias | ✅ Implementado | Hilt | | 🐛 | Manejo de Errores | ✅ Implementado | Sealed class para estados de UI | | 🧪 | Pruebas Unitarias | ✅ Implementado | JUnit y MockK | | ✨ | Mejoras de UI | ✅ Implementado | Animaciones y efecto Shimmer | | 🔐 | Autenticación | ✅ Implementado | Firebase Authentication | | ⚙️ | Ajustes | ✅ Implementado | Pantalla de ajustes | | 📏 | Dibujo 2D | ✅ Implementado | Dibujo 2D con medidas | | 🖼️ | Pantalla Dinámica | ✅ Implementado | Pantalla dinámica por pieza | | 💾 | Guardar Inspecciones | ✅ Implementado | Guardar inspecciones en la base de datos | | 🔬 | Procesamiento de Imagen | ✅ Implementado | Técnicas avanzadas de procesamiento de imagen | | 📷 | Calibración de Cámara | ✅ Implementado | Técnicas avanzadas de calibración de cámara |

✅ Checklist Constructivo de Celestic – Por Etapas Técnicas

🧱 1. Estructura Base del Proyecto 📦 Archivos y carpetas:

MainActivity.kt
AppNavigation.kt
Carpeta ui/
Carpeta model/
Carpeta data/
Carpeta theme/
Carpeta utils/ 🎨 Recursos XML:
colors.xml
strings.xml
dimens.xml
themes.xml 🎨 Tema y estilos:
CelesticTheme.kt
Typography.kt
Shape.kt (opcional para bordes)
🧩 2. Modelo de Datos + Persistencia 📄 Modelos:

DetectionItem.kt
DetectionStatus.kt (enum 🟢🟡🔴)
BoundingBox.kt
ReportEntry.kt
CameraCalibrationData.kt
DetectedFeature.kt
ReportConfig.kt 🗃️ Room Database:
DetectionDao.kt
DetectionDatabase.kt
DetectionRepository.kt 📁 Archivos externos:
calibration.json
trazabilidad.json (por lectura QR)
config_report.json (configuraciones por usuario)
🎥 3. Módulo Cámara + Análisis de Imagen 📷 Captura:

CameraView.kt
CameraUtils.kt 🔍 Procesamiento:
FrameAnalyzer.kt
Función: detectEdges()
Función: detectMarkers()
Función: classifyImageAI()
Función: applyCalibration()
Función: extractDimensionsFromContours() 📐 Calibración:
CalibrationManager.kt
detectCharucoPattern()
generateCalibrationMatrix()
saveCalibrationToJson()
loadCalibrationFromJson()
🧠 4. Inteligencia Artificial integrada 🧠 IA Android:

Modelo .tflite o .pt guardado en assets/
ImageClassifier.kt
Función: runInference(bitmap)
Función: mapPredictionToFeatureType() 🐍 IA Python:
Script de entrenamiento train_model.py
Dataset etiquetado data/train_images/
Exportación compatible con Android
📊 5. Interfaz y Pantallas 🖼️ Pantallas Compose:

DashboardScreen.kt
CameraScreen.kt
DetailsScreen.kt
ReportRequestDialog.kt
InspectionPreviewScreen.kt (opcional visualización previa)
CalibrationScreen.kt (para configuración manual) 📦 Componentes Composables:
FeatureCard.kt
StatusIndicator.kt
MeasurementOverlay.kt 📍 Navegación:
NavigationRoutes.kt
NavigationGraph.kt
🧾 6. Trazabilidad QR / ArUco / AprilTag 🧾 Escaneo:

QRScanner.kt
Función: startQrScan()
Función: decodeBarcode()
Vinculación con DetectionItem 🔲 Marcadores:
ArUcoManager.kt
AprilTagManager.kt (via JNI o librería integrada) 🗃️ Datos enlazados:
Base de datos trazabilidad.json
Conexión visual en DetailsScreen.kt
📄 7. Reporte de inspección (PDF / Word / JSON) 📄 Generación:

ReportGenerator.kt
generatePdfFromDetections()
generateWordFromDetections()
exportJsonSummary()
filterDetectionsByStatus() 🖼️ UI:
Botón en DashboardScreen.kt o DetailsScreen.kt
Selector: PDF, Word o JSON 📁 Exportación:
Carpeta /storage/emulated/0/Celestic/Reports/
Nombre sugerido: ReporteCelestic_Lote123.pdf
🎨 8. Recursos Visuales 📷 Imagen / Iconos en drawable/:

charuco_pattern.png
logo_celestic.png
icon_inspection.png
icon_pdf.png, icon_word.png
status_green.png, status_yellow.png, status_red.png
graph_placeholder.png

## Post-producción
- Crear una aplicación base para añadir módulos.
- Desarrollar módulos para otros dominios como automoción, aeronáutica, etc.
- Se utilizará ML Kit para el módulo base para multiplataforma.
