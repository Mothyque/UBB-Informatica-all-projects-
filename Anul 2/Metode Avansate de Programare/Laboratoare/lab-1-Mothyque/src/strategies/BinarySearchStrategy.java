package strategies;

import domain.Duck;
import domain.Lane;
import domain.Race;

public class BinarySearchStrategy implements OptimisationStrategy
{

    @Override
    public void solveRace(Race race)
    {
        Duck[] ducks = race.getDucks();
        Lane[] lanes = race.getLanes();
        double[][] time = new double[ducks.length][lanes.length];
        double maxTime = 0;
        for (int i = 0; i < ducks.length; i++)
        {
            for (int j = 0; j < lanes.length; j++)
            {
                time[i][j] = 2 * (lanes[j].getDistance() / ducks[i].getSpeed());
                if (time[i][j] > maxTime)
                {
                    maxTime = time[i][j];
                }
            }
        }
        double[] orderedTimes = getOrderedTimes(time);
        int left = 0, right = orderedTimes.length - 1;
        Duck[] sortedDucks = getDuckSortedbyResistance(ducks);
        Duck[] result = new Duck[lanes.length];
        double bestTime = -1;
        while(left <= right)
        {
            race.clearAssignments();
            int mid = (left + right) / 2;
            if(isPossible(ducks, sortedDucks, lanes, time, mid, orderedTimes, race))
            {
                bestTime = orderedTimes[mid];
                for(int i = 0; i < lanes.length; i++)
                {
                    result[i] = race.getAssignedDuckInLane(i);
                }
                right = mid - 1;
            }
            else
            {
                left = mid + 1;
            }
        }
        if (bestTime == -1)
        {
            System.out.println("No valid assignment found.");
            return;
        }
        else
        {
            System.out.printf("Race finished!" + " Minimum time required: %.3f seconds%n", bestTime);
            System.out.println("Duck assignments to lanes:");
        }
        for (int i = 0; i < lanes.length; i++)
        {
            System.out.printf("Lane L%d: |  Duck D%d:  Time = %.2f | Resistance: %d %n", i + 1,  result[i].getId() + 1, time[result[i].getId()][i], result[i].getResistance());
        }

}

    public Duck[] getDuckSortedbyResistance(Duck[] ducks)
    {
        Duck[] sortedDucks = ducks.clone();
        boolean swapped = true;
        while (swapped)
        {
            swapped = false;
            for (int i = 0; i < sortedDucks.length - 1; i++)
            {
                if (sortedDucks[i].getResistance() > sortedDucks[i + 1].getResistance())
                {
                    Duck temp = sortedDucks[i];
                    sortedDucks[i] = sortedDucks[i + 1];
                    sortedDucks[i + 1] = temp;
                    swapped = true;
                }
            }
        }
        return sortedDucks;
    }

    public boolean isPossible(Duck[] ducks, Duck[] sortedDucks, Lane[] lanes, double[][] time, int mid, double[] orderedTimes, Race race)
    {
        boolean[] used = new boolean[ducks.length];
         for (int i = 0; i < lanes.length; i++)
        {
            boolean assigned = false;
            for(int j = 0; j < ducks.length; j++)
            {
                Duck duck = sortedDucks[j];
                int duckIndex = duck.getId();
                if (!used[duckIndex] && time[duckIndex][i] <= orderedTimes[mid] && isResistanceValid(duck, race, i) && !lanes[i].isOccupied())
                {
                    race.assignDuckToLane(duck, i);
                    used[duckIndex] = true;
                    assigned = true;
                    break;
                }
            }
            if (!assigned)
            {
                return false;
            }
        }
        return true;
    }

    public boolean isResistanceValid(Duck duck, Race race, int laneIndex)
    {
        if (laneIndex == 0)
        {
            return true;
        }
        return duck.getResistance() >=  race.getAssignedDuckInLane(laneIndex - 1).getResistance();
    }

    public double[] getOrderedTimes(double[][] times)
    {
        double[] orderedTimes = new double[times.length * times[0].length];
        int index = 0;
        for (int i = 0; i < times.length; i++)
        {
            for (int j = 0; j < times[0].length; j++)
            {
                orderedTimes[index++] = times[i][j];
            }
        }
        orderedTimes = sortTimes(orderedTimes);
        orderedTimes = removeDuplicates(orderedTimes);
        System.out.println();
        return orderedTimes;
    }

    public double[] sortTimes(double[] times)
    {
        double[] sortedTimes = times.clone();
        boolean swapped =true;
        while (swapped)
        {
            swapped = false;
            for (int i = 0; i < times.length - 1; i++)
            {
                if (sortedTimes[i] > sortedTimes[i + 1])
                {
                    double temp = sortedTimes[i];
                    sortedTimes[i] = sortedTimes[i + 1];
                    sortedTimes[i + 1] = temp;
                    swapped = true;
                }
            }
        }
        return sortedTimes;
    }

    public double[] removeDuplicates(double[] times)
    {
        int n = times.length;
        if (n == 0 || n == 1)
        {
            return times;
        }
        double[] tmp = new double[n];
        int j = 0;
        for (int i = 0; i < n - 1; i++)
        {
            if (times[i] != times[i + 1])
            {
                tmp[j++] = times[i];
            }
        }
        tmp[j++] = times[n - 1];
        double[] result = new double[j];
        for (int i = 0; i < j; i++)
        {
            result[i] = tmp[i];
        }
        return result;
    }
}