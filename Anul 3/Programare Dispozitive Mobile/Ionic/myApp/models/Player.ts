export interface Player {
    id: string;
    name: string;
    team: string;
    pointsPerGame: number;
    birthDate: string;
    isCaptain: boolean;
}
export type PlayerInput = Omit<Player, 'id'>;