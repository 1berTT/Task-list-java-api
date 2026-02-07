import type { UseQueryOptions } from "@tanstack/react-query";
import { api } from "@/services/api";

export type UserData = { id: string; name: string; email: string };

export const currentUserKeys = {
  all: ["currentUser"] as const,
  detail: (id: string | null, isAdmin: boolean) =>
    [...currentUserKeys.all, id, isAdmin] as const,
};

export function currentUserQueryOptions(
  id: string | null,
  isAdmin: boolean,
): UseQueryOptions<UserData> {
  return {
    queryKey: currentUserKeys.detail(id, isAdmin),
    queryFn: async () => {
      if (!id) throw new Error("No user id");
      const endpoint = isAdmin
        ? `/admin/load/${id}`
        : `/user/load/${id}`;
      const { data } = await api.get<UserData>(endpoint);
      return data;
    },
    enabled: !!id,
    staleTime: 1000 * 60 * 5,
  };
}
