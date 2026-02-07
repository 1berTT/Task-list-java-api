import { NextRequest, NextResponse } from "next/server";

function decodeJwt(token: string) {
  try {
    const payload = token.split(".")[1];
    return JSON.parse(Buffer.from(payload, "base64").toString());
  } catch {
    return null;
  }
}

export function middleware(req: NextRequest) {
  const token = req.cookies.get("token")?.value;
  const { pathname } = req.nextUrl;

  const publicRoutes = ["/login"];

  // 🔓 Rota pública
  if (publicRoutes.includes(pathname)) {
    if (token) {
      const payload = decodeJwt(token);
      const isValid =
        payload &&
        payload.exp &&
        payload.exp * 1000 >= Date.now();
      if (isValid) {
        const roles: string[] = payload.roles || [];
        const dashboardByRole = roles.includes("ADMIN")
          ? "/dashboard/admin"
          : "/dashboard/user";
        return NextResponse.redirect(new URL(dashboardByRole, req.url));
      }
    }
    return NextResponse.next();
  }

  // ❌ Tentando rota privada sem token
  if (!token) {
    return NextResponse.redirect(new URL("/login", req.url));
  }

  const payload = decodeJwt(token);

  // ❌ Token inválido
  if (!payload) {
    const res = NextResponse.redirect(new URL("/login", req.url));
    res.cookies.delete("token");
    return res;
  }

  // ⏰ Token expirado
  if (payload.exp * 1000 < Date.now()) {
    const res = NextResponse.redirect(new URL("/login", req.url));
    res.cookies.delete("token");
    return res;
  }

  const roles: string[] = payload.roles || [];
  const isAdmin = roles.includes("ADMIN");
  const dashboardByRole = isAdmin ? "/dashboard/admin" : "/dashboard/user";

  // /dashboard exato → redireciona para o dashboard correto conforme role
  if (pathname === "/dashboard") {
    return NextResponse.redirect(new URL(dashboardByRole, req.url));
  }

  // 🔐 USER tentando acessar rota ADMIN
  if (pathname.startsWith("/dashboard/admin") && !isAdmin) {
    return NextResponse.redirect(new URL("/dashboard/user", req.url));
  }

  // 🔐 ADMIN tentando acessar rota USER (opcional: redireciona para admin)
  if (pathname.startsWith("/dashboard/user") && isAdmin) {
    return NextResponse.redirect(new URL("/dashboard/admin", req.url));
  }

  return NextResponse.next();
}

// 👇 FICA AQUI, NO MESMO ARQUIVO
export const config = {
  matcher: ["/((?!_next/static|_next/image|favicon.ico).*)"],
};
