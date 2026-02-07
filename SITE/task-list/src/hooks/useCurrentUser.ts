"use client";

import { useQuery } from "@tanstack/react-query";
import { getTokenData, isAdminFromRoles } from "@/lib/auth";
import { currentUserQueryOptions } from "@/queries";

export function useCurrentUser() {
  const tokenData = getTokenData();
  const id = tokenData?.id ?? null;
  const isAdmin = isAdminFromRoles(tokenData?.roles ?? []);

  const { data: user, isLoading } = useQuery(currentUserQueryOptions(id, isAdmin));

  return {
    user,
    isLoading,
    isAuthenticated: !!id,
  };
}
