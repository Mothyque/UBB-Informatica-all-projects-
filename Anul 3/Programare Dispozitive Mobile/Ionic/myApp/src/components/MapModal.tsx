import React, { useState } from 'react';
import {
  IonModal,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonButtons,
  IonButton,
  IonContent,
  createAnimation
} from '@ionic/react';
import { MapContainer, TileLayer, Marker, useMapEvents } from 'react-leaflet';
import L from 'leaflet';

import markerIcon2x from 'leaflet/dist/images/marker-icon-2x.png';
import markerIcon from 'leaflet/dist/images/marker-icon.png';
import markerShadow from 'leaflet/dist/images/marker-shadow.png';

delete (L.Icon.Default.prototype as any)._getIconUrl;
L.Icon.Default.mergeOptions({
  iconUrl: markerIcon,
  iconRetinaUrl: markerIcon2x,
  shadowUrl: markerShadow,
});

interface MapModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSelectLocation?: (lat: number, lng: number) => void;
  initialLat?: number;
  initialLng?: number;
  readOnly?: boolean; 
}

const LocationMarker: React.FC<{
  position: [number, number] | null;
  setPosition: (pos: [number, number]) => void;
  readOnly?: boolean;
}> = ({ position, setPosition, readOnly }) => {
  useMapEvents({
    click(e) {
      if (!readOnly) {
        setPosition([e.latlng.lat, e.latlng.lng]);
      }
    },
  });

  return position ? <Marker position={position} /> : null;
};

export const MapModal: React.FC<MapModalProps> = ({
  isOpen,
  onClose,
  onSelectLocation,
  initialLat,
  initialLng,
  readOnly = false,
}) => {
  const defaultCenter: [number, number] = [
    initialLat ?? 37.9838,
    initialLng ?? 23.7275,
  ];

  const [selectedPos, setSelectedPos] = useState<[number, number] | null>(
    initialLat && initialLng ? [initialLat, initialLng] : null
  );

  const handleSave = () => {
    if (selectedPos && onSelectLocation) {
      onSelectLocation(selectedPos[0], selectedPos[1]);
    }
    onClose();
  };

  const customEnterAnimation = (baseEl: HTMLElement) => {
    const root = baseEl.shadowRoot || baseEl;
    const backdropAnimation = createAnimation()
      .addElement(root.querySelector('ion-backdrop')!)
      .fromTo('opacity', '0.01', 'var(--backdrop-opacity)');

    const wrapperAnimation = createAnimation()
      .addElement(root.querySelector('.modal-wrapper')!)
      .keyframes([
        { offset: 0, opacity: '0', transform: 'scale(0.7)' },
        { offset: 1, opacity: '0.99', transform: 'scale(1)' },
      ]);

      return createAnimation()
        .addElement(baseEl)
        .addAnimation(backdropAnimation)
        .addAnimation(wrapperAnimation)
        .duration(500);
  };

  const customLeaveAnimation = (baseEl: HTMLElement) => {
    return customEnterAnimation(baseEl).direction('reverse');
  }

  return (
    <IonModal isOpen={isOpen} onDidDismiss={onClose} enterAnimation={customEnterAnimation} leaveAnimation={customLeaveAnimation}>
      <IonHeader>
        <IonToolbar color="primary">
          <IonTitle>{readOnly ? 'Player / Arena Location' : 'Select Location on Map'}</IonTitle>
          <IonButtons slot="end">
            <IonButton onClick={onClose}>Close</IonButton>
          </IonButtons>
        </IonToolbar>
      </IonHeader>

      <IonContent>
        <div style={{ height: '80%', width: '100%' }}>
          <MapContainer
            center={selectedPos || defaultCenter}
            zoom={13}
            style={{ height: '100%', width: '100%' }}
          >
            <TileLayer
              attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <LocationMarker
              position={selectedPos}
              setPosition={setSelectedPos}
              readOnly={readOnly}
            />
          </MapContainer>
        </div>

        <div style={{ padding: '16px', textAlign: 'center' }}>
          {selectedPos ? (
            <p style={{ margin: '8px 0', fontSize: '0.9rem' }}>
              Lat: <b>{selectedPos[0].toFixed(5)}</b>, Lng: <b>{selectedPos[1].toFixed(5)}</b>
            </p>
          ) : (
            <p style={{ color: 'gray' }}>Apasă pe hartă pentru a plasa markerul.</p>
          )}

          {!readOnly && (
            <IonButton expand="block" disabled={!selectedPos} onClick={handleSave}>
              Confirm Selected Location
            </IonButton>
          )}
        </div>
      </IonContent>
    </IonModal>
  );
};