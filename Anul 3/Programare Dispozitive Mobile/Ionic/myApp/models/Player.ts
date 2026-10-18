export interface Player {
    id: string;
    name: string;
    team: string;
    pointsPerGame: number;
    birthDate: Date;
    isCaptain: boolean;
}
export type PlayerInput = Omit<Player, 'id'>;