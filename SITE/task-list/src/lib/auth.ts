export function getToken(): string | null {
  if (typeof window === "undefined") return null;
  return (
    localStorage.getItem("token") ??
    document.cookie.match(/token=([^;]+)/)?.[1]?.trim() ??
    null
  );
}

export function decodeJwtPayload(
  token: string,
): { sub?: string; roles?: string[]; exp?: number } | null {
  try {
    const payload = token.split(".")[1];
    const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");
    return JSON.parse(atob(base64)) as {
      sub?: string;
      roles?: string[];
      exp?: number;
    };
  } catch {
    return null;
  }
}

export function isTokenValid(token: string): boolean {
  const payload = decodeJwtPayload(token);
  if (!payload?.exp) return false;
  return payload.exp * 1000 > Date.now();
}

export function getTokenData(): { id: string; roles: string[] } | null {
  const token = getToken();
  if (!token || !isTokenValid(token)) return null;
  const payload = decodeJwtPayload(token);
  if (!payload?.sub) return null;
  return {
    id: payload.sub,
    roles: payload.roles ?? [],
  };
}

export function isAdminFromRoles(roles: string[]): boolean {
  return roles.some((r) => r.toUpperCase() === "ADMIN");
}
