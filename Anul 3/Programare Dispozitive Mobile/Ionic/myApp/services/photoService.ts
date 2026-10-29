import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import { Filesystem, Directory } from '@capacitor/filesystem';

const ALLOWED_FORMATS = ['jpeg', 'jpg', 'png', 'webp']
const MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024; 

export interface UserPhoto {
  filepath: string;
  webviewPath?: string;
  base64?: string;
}

export const takePhoto = async (): Promise<UserPhoto> => {
  const cameraPhoto = await Camera.getPhoto({
    resultType: CameraResultType.Base64,
    source: CameraSource.Prompt,
    quality: 80,
  });

  const format = (cameraPhoto.format || '').toLowerCase();
    if (!ALLOWED_FORMATS.includes(format)) {
        throw new Error(`Formatul fișierului nu este acceptat. Formate permise: ${ALLOWED_FORMATS.join(', ')}`);
    }
    
  const base64String = cameraPhoto.base64String || '';
  const byteSize = (base64String.length * 3) / 4;

  if (byteSize > MAX_FILE_SIZE_BYTES) {
        throw new Error(`Fișierul este prea mare. Dimensiunea maximă permisă este de ${MAX_FILE_SIZE_BYTES / (1024 * 1024)} MB.`);
    }

  const fileName = `player_${Date.now()}.jpeg`;
  const base64Data = `data:image/jpeg;base64,${cameraPhoto.base64String}`;

  try {
    const savedFile = await Filesystem.writeFile({
      path: fileName,
      data: cameraPhoto.base64String || '',
      directory: Directory.Data,
    });

    console.log('Poza a fost salvată în Filesystem la calea:', savedFile.uri);
  } catch (error) {
    console.warn('Filesystem salvare locală avertisment (browser fallback):', error);
  }

  return {
    filepath: fileName,
    webviewPath: base64Data,
    base64: base64Data,
  };
};