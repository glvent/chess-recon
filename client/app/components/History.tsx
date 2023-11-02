import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRotateRight } from "@fortawesome/free-solid-svg-icons";
import { Move } from "@/app/types/chessTypes";
import { FC, ReactElement } from "react";

interface HistoryProps {
  history: Move[] | null;
}

const History = ({ history }: HistoryProps) => {
  return (
    <div className="self-center h-[110%] w-80 [background:rgb(33,32,30)] rounded-md">
      <table className="w-full">
        <tbody>
          {history && (
            <>
              {history
                .reduce<Move[][]>((acc, move, index) => {
                  if (index % 2 === 0) {
                    acc.push([move]);
                  } else {
                    acc[acc.length - 1].push(move);
                  }
                  return acc;
                }, [])
                .map((movePair: Move[], moveIndex: number) => (
                  <tr
                    key={moveIndex}
                    className={`${
                      moveIndex % 2 !== 0 && "[background:rgb(50,48,45)]"
                    } text-sm font-black flex px-2 py-1`}
                  >
                    <td className="">{moveIndex + 1}.</td>
                    <td className="px-8">{movePair[0]?.notation}</td>
                    <td className="px-8">{movePair[1]?.notation || ""}</td>
                  </tr>
                ))}
            </>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default History;
