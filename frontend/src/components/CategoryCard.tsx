import { Tag } from "lucide-react";

interface CategoryCardProps {
  name: string;
  description?: string;
  onClick?: () => void;
}

export default function CategoryCard({ name, description, onClick }: CategoryCardProps) {
  return (
    <button
      onClick={onClick}
      className="group w-full rounded-2xl border bg-white p-4 text-left shadow-sm transition hover:-translate-y-1 hover:border-blue-200 hover:shadow-lg"
    >
      <div className="flex items-center gap-3">
        <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-blue-50 text-blue-600">
          <Tag className="h-5 w-5" />
        </div>
        <div>
          <p className="font-semibold text-gray-900">{name}</p>
          {description && <p className="text-xs text-gray-500">{description}</p>}
        </div>
      </div>
    </button>
  );
}
