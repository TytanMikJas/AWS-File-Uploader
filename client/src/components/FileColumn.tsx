import FileDto from "../store/dto/File.dto";
import FileRow from "./FileRow";

const MOCK_FILES: FileDto[] = [
  {
    fileId: 1,
    fileName: "MikolajJastrzebski.jpg",
    fileUrl:
      "https://media.licdn.com/dms/image/D4D03AQExAmS4kJCU3w/profile-displayphoto-shrink_800_800/0/1690728427435?e=1721865600&v=beta&t=IoAyE_wS8FkMUuA7fwOf-KLKWRCu0rrClZ7l0GZ3CHE",
  },
  {
    fileId: 22,
    fileName: "KacperGrobelny.jpg",
    fileUrl:
      "https://media.licdn.com/dms/image/D4D03AQFXnf0ZboufOg/profile-displayphoto-shrink_800_800/0/1705692350337?e=1721865600&v=beta&t=moJ-K2I5_cNDjXY7rHxC7Cbxc7FNGDUbJmRNYCPldaE",
  },
  {
    fileId: 333,
    fileName: "WiktorStankiewicz.jpg",
    fileUrl:
      "https://media.licdn.com/dms/image/D4D03AQHyX2A0viu3kA/profile-displayphoto-shrink_800_800/0/1705005584721?e=1721865600&v=beta&t=xEOgnnanNmGQ0bKsD_n8d8fvdxcKk_OIY4rCkdnrRV4",
  },
  {
    fileId: 4444,
    fileName: "DamianSawko.jpg",
    fileUrl: "https://www.google.com",
  },
];

interface FileColumnProps {
  files?: FileDto[];
}

export default function FileColumn({ files = MOCK_FILES }: FileColumnProps) {
  return (
    <div className="w-1/4 divide-y-2 divide-slate-500">
      <div className="flex pb-2">
        <p className="w-2/12 font-semibold text-gray-400">Id</p>
        <p className="w-6/12 font-semibold text-gray-400">File name</p>
      </div>
      <div className="flex flex-col divide-y-2 divide-slate-500">
        {files.map((file) => (
          <FileRow key={file.fileId} file={file} />
        ))}
      </div>
    </div>
  );
}
