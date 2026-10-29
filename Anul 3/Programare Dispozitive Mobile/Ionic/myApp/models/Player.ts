export interface Player {
    id: string;
    name: string;
    team: string;
    pointsPerGame: number;
    birthDate: Date;
    isCaptain: boolean;
    userId?: string;
    notSynced?: boolean; 
    photo?: string;
    latitude?: number;
    longitude?: number
}
export type PlayerInput = Omit<Player, 'id' | 'notSynced'>;

export type PlayerResponse = Omit<Player, 'birthDate'> & {
    birthDate: Date | string;
};

export const normalizePlayer = (player: PlayerResponse): Player => ({
    ...player,
    birthDate: player.birthDate instanceof Date
        ? player.birthDate
        : new Date(player.birthDate),
});